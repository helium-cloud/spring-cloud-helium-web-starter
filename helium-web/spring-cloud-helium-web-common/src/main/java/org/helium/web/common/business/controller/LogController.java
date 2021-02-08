package org.helium.web.common.business.controller;

import org.helium.web.common.business.entity.Log;
import org.helium.web.common.business.service.LogService;
import org.helium.web.common.common.utils.PageUtil;
import org.helium.web.common.common.utils.ResultUtil;
import org.helium.web.common.common.vo.PageVo;
import org.helium.web.common.common.vo.Result;
import org.helium.web.common.common.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @author coral
 */

@RestController
@Api(description = "日志管理接口")
@RequestMapping("/log")
@Transactional
@ConditionalOnProperty(prefix = "mp.admin", name = "enable", havingValue = "true")
public class LogController{



    @Autowired
    private LogService logService;

    @RequestMapping(value = "/getAllByPage",method = RequestMethod.GET)
    @ApiOperation(value = "分页获取全部")
    public Result<Object> getAllByPage(@RequestParam(required = false) Integer type,
                                       @RequestParam String key,
                                       @ModelAttribute SearchVo searchVo,
                                       @ModelAttribute PageVo pageVo){

        Page<Log> log = logService.findByConfition(type, key, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Object>().setData(log);
    }

    @RequestMapping(value = "/delByIds/{ids}",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除")
    public Result<Object> delByIds(@PathVariable String[] ids){

        for(String id : ids){
            logService.delete(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("删除成功");
    }

    @RequestMapping(value = "/delAll",method = RequestMethod.DELETE)
    @ApiOperation(value = "全部删除")
    public Result<Object> delAll(){
        logService.deleteAll();
        return new ResultUtil<Object>().setSuccessMsg("删除成功");
    }
}
