package com.coral.mini.program.common.business.controller;

import com.coral.mini.program.common.business.entity.Permission;
import com.coral.mini.program.common.business.entity.RolePermission;
import com.coral.mini.program.common.business.entity.User;
import com.coral.mini.program.common.business.service.PermissionService;
import com.coral.mini.program.common.business.service.RolePermissionService;
import com.coral.mini.program.common.business.service.mybatis.IPermissionService;
import com.coral.mini.program.common.common.constant.CommonConstant;
import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.utils.SecurityUtil;
import com.coral.mini.program.common.common.vo.Result;
import com.coral.mini.program.common.config.security.permission.MySecurityMetadataSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author coral
 */
@Slf4j
@RestController
@Api(description = "菜单/权限管理接口")
@RequestMapping("/permission")
@Transactional
@ConditionalOnProperty(prefix = "mp.admin", name = "enable", havingValue = "true")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @RequestMapping(value = "/getMenuList",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户页面菜单数据")
    public Result<List<Permission>> getAllMenuList(){

        List<Permission> list = new ArrayList<>();
        // 读取缓存
        User u = securityUtil.getCurrUser();
        log.info("getMenuList Start:\n{}", u.getUsername());

        // 用户所有权限 已排序去重
        list = iPermissionService.findByUserId(u.getId());

        List<Permission> menuList = new ArrayList<>();
        //筛选一级页面
        for(Permission p : list){
            if(CommonConstant.PERMISSION_PAGE.equals(p.getType())&&CommonConstant.LEVEL_ONE.equals(p.getLevel())){
                menuList.add(p);
            }
        }
        //筛选二级页面
        List<Permission> secondMenuList = new ArrayList<>();
        for(Permission p : list){
            if(CommonConstant.PERMISSION_PAGE.equals(p.getType())&&CommonConstant.LEVEL_TWO.equals(p.getLevel())){
                secondMenuList.add(p);
            }
        }
        //筛选二级页面拥有的按钮权限
        List<Permission> buttonPermissions = new ArrayList<>();
        for(Permission p : list){
            if(CommonConstant.PERMISSION_OPERATION.equals(p.getType())&&CommonConstant.LEVEL_THREE.equals(p.getLevel())){
                buttonPermissions.add(p);
            }
        }

        //匹配二级页面拥有权限
        for(Permission p : secondMenuList){
            List<String> permTypes = new ArrayList<>();
            for(Permission pe : buttonPermissions){
                if(p.getId().equals(pe.getParentId())){
                    permTypes.add(pe.getButtonType());
                }
            }
            p.setPermTypes(permTypes);
        }
        //匹配一级页面拥有二级页面
        for(Permission p : menuList){
            List<Permission> secondMenu = new ArrayList<>();
            for(Permission pe : secondMenuList){
                if(p.getId().equals(pe.getParentId())){
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }
        // 缓存
        return new ResultUtil<List<Permission>>().setData(menuList);
    }

    @RequestMapping(value = "/getAllList",method = RequestMethod.GET)
    @ApiOperation(value = "获取权限菜单树")
    public Result<List<Permission>> getAllList(){
        log.info("getAllList.");
        //一级
        List<Permission> list = permissionService.findByLevelOrderBySortOrder(CommonConstant.LEVEL_ONE);
        //二级
        for(Permission p1 : list){
            List<Permission> children1 = permissionService.findByParentIdOrderBySortOrder(p1.getId());
            p1.setChildren(children1);
            //三级
            for(Permission p2 : children1){
                List<Permission> children2 = permissionService.findByParentIdOrderBySortOrder(p2.getId());
                p2.setChildren(children2);
            }
        }
        return new ResultUtil<List<Permission>>().setData(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Permission> add(@ModelAttribute Permission permission){

        // 判断拦截请求的操作权限按钮名是否已存在
        if(CommonConstant.PERMISSION_OPERATION.equals(permission.getType())){
            List<Permission> list = permissionService.findByTitle(permission.getTitle());
            if(list!=null&&list.size()>0){
                return new ResultUtil<Permission>().setErrorMsg("名称已存在");
            }
        }
        Permission u = permissionService.save(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();

        return new ResultUtil<Permission>().setData(u);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Permission> edit(@ModelAttribute Permission permission){

        // 判断拦截请求的操作权限按钮名是否已存在
        if(CommonConstant.PERMISSION_OPERATION.equals(permission.getType())){
            // 若名称修改
            Permission p = permissionService.get(permission.getId());
            if(!p.getTitle().equals(permission.getTitle())){
                List<Permission> list = permissionService.findByTitle(permission.getTitle());
                if(list!=null&&list.size()>0){
                    return new ResultUtil<Permission>().setErrorMsg("名称已存在");
                }
            }
        }
        Permission u = permissionService.update(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        return new ResultUtil<Permission>().setData(u);
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@PathVariable String[] ids){

        for(String id:ids){
            List<RolePermission> list = rolePermissionService.findByPermissionId(id);
            if(list!=null&&list.size()>0){
                rolePermissionService.deleteByPermissionId(id);
            }
        }
        for(String id:ids){
            permissionService.delete(id);
        }
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "搜索菜单")
    public Result<List<Permission>> searchPermissionList(@RequestParam String title){

        List<Permission> list = permissionService.findByTitleLikeOrderBySortOrder("%"+title+"%");
        return new ResultUtil<List<Permission>>().setData(list);
    }
}
