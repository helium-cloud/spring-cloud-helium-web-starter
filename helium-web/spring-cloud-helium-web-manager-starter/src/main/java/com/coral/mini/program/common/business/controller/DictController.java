package com.coral.mini.program.common.business.controller;

import com.coral.mini.program.common.business.entity.Dict;
import com.coral.mini.program.common.business.service.DictDataService;
import com.coral.mini.program.common.business.service.DictService;
import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author coral
 */
@RestController
@Api(description = "字典管理接口")
@RequestMapping("/dict")
@Transactional
@ConditionalOnProperty(prefix = "mp.admin", name = "enable", havingValue = "true")
public class DictController{

    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;


    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<Dict>> getAll(){

        List<Dict> list = dictService.findAllOrderBySortOrder();
        return new ResultUtil<List<Dict>>().setData(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(@ModelAttribute Dict dict){

        if(dictService.findByType(dict.getType())!=null){
            return new ResultUtil<Object>().setErrorMsg("字典类型Type已存在");
        }
        dictService.save(dict);
        return new ResultUtil<Object>().setSuccessMsg("添加成功");
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(@ModelAttribute Dict dict){

        Dict old = dictService.get(dict.getId());
        // 若type修改判断唯一
        if(!old.getType().equals(dict.getType())&&dictService.findByType(dict.getType())!=null){
            return new ResultUtil<Object>().setErrorMsg("字典类型Type已存在");
        }
        dictService.update(dict);
        return new ResultUtil<Object>().setSuccessMsg("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{id}",method = RequestMethod.DELETE)
    @ApiOperation(value = "通过id删除")
    public Result<Object> delAllByIds(@PathVariable String id){


        Dict dict = dictService.get(id);
        dictService.delete(id);
        dictDataService.deleteByDictId(id);

        return new ResultUtil<Object>().setSuccessMsg("删除成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "搜索字典")
    public Result<List<Dict>> searchPermissionList(@RequestParam String key){

        List<Dict> list = dictService.findByTitleOrTypeLike(key);
        return new ResultUtil<List<Dict>>().setData(list);
    }
}
