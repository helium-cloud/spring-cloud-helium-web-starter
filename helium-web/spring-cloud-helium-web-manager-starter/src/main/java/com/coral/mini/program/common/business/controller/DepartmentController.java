package com.coral.mini.program.common.business.controller;

import com.coral.mini.program.common.business.entity.Department;
import com.coral.mini.program.common.business.entity.DepartmentHeader;
import com.coral.mini.program.common.business.entity.User;
import com.coral.mini.program.common.business.service.DepartmentHeaderService;
import com.coral.mini.program.common.business.service.DepartmentService;
import com.coral.mini.program.common.business.service.RoleDepartmentService;
import com.coral.mini.program.common.business.service.UserService;
import com.coral.mini.program.common.common.constant.CommonConstant;
import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.utils.SecurityUtil;
import com.coral.mini.program.common.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author coral
 */
@RestController
@Api(description = "部门管理接口")
@RequestMapping("/department")
@CacheConfig(cacheNames = "department")
@Transactional
@ConditionalOnProperty(prefix = "mp.admin", name = "enable", havingValue = "true")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDepartmentService roleDepartmentService;

    @Autowired
    private DepartmentHeaderService departmentHeaderService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getByParentId/{parentId}",method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<List<Department>> getByParentId(@PathVariable String parentId,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

        List<Department> list = new ArrayList<>();
        User u = securityUtil.getCurrUser();

        list = departmentService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
        list = setInfo(list);
        return new ResultUtil<List<Department>>().setData(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(@ModelAttribute Department department){

        Department d = departmentService.save(department);
        // 同步该节点缓存
        User u = securityUtil.getCurrUser();
        // 如果不是添加的一级 判断设置上级为父节点标识
        if(!CommonConstant.PARENT_ID.equals(department.getParentId())){
            Department parent = departmentService.get(department.getParentId());
            if(parent.getParent()==null||!parent.getParent()){
                parent.setParent(true);
                departmentService.update(parent);
            }
        }
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(@ModelAttribute Department department,
                               @RequestParam(required = false) String[] mainHeader,
                               @RequestParam(required = false) String[] viceHeader){

        Department d = departmentService.update(department);
        // 先删除原数据
        departmentHeaderService.deleteByDepartmentId(department.getId());
        for(String id:mainHeader){
            DepartmentHeader dh = new DepartmentHeader();
            dh.setUserId(id);
            dh.setDepartmentId(d.getId());
            dh.setType(CommonConstant.HEADER_TYPE_MAIN);
            departmentHeaderService.save(dh);
        }
        for(String id:viceHeader){
            DepartmentHeader dh = new DepartmentHeader();
            dh.setUserId(id);
            dh.setDepartmentId(d.getId());
            dh.setType(CommonConstant.HEADER_TYPE_VICE);
            departmentHeaderService.save(dh);
        }
        // 手动删除所有部门缓存
        return new ResultUtil<Object>().setSuccessMsg("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable String[] ids){

        for(String id:ids){
            List<User> list = userService.findByDepartmentId(id);
            if(list!=null&&list.size()>0){
                return new ResultUtil<Object>().setErrorMsg("删除失败，包含正被用户使用关联的部门");
            }
        }
        for(String id:ids){
            departmentService.delete(id);
            // 删除关联数据权限
            roleDepartmentService.deleteByDepartmentId(id);
            // 删除关联部门负责人
            departmentHeaderService.deleteByDepartmentId(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public Result<List<Department>> searchByTitle(@RequestParam String title,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter){

        List<Department> list = departmentService.findByTitleLikeOrderBySortOrder("%"+title+"%", openDataFilter);
        list = setInfo(list);
        return new ResultUtil<List<Department>>().setData(list);
    }

    public List<Department> setInfo(List<Department> list){

        // lambda表达式
        list.forEach(item -> {
            if(!CommonConstant.PARENT_ID.equals(item.getParentId())){
                Department parent = departmentService.get(item.getParentId());
                item.setParentTitle(parent.getTitle());
            }else{
                item.setParentTitle("一级部门");
            }
            // 设置负责人
            item.setMainHeader(departmentHeaderService.findHeaderByDepartmentId(item.getId(), CommonConstant.HEADER_TYPE_MAIN));
            item.setViceHeader(departmentHeaderService.findHeaderByDepartmentId(item.getId(), CommonConstant.HEADER_TYPE_VICE));
        });
        return list;
    }
}
