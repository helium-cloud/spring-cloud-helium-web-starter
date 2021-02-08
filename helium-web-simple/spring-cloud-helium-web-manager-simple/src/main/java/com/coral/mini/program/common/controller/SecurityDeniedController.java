package com.coral.mini.program.common.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * 数据统计
 */
@Api(value = "权限认证", description = "权限认证")
@Slf4j
@RestController
@RequestMapping("/common/needLogin")
public class SecurityDeniedController {
    @ResponseBody
    @GetMapping
    public JSONObject accessDenied(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("reason", "您没有权限访问");
        return jsonObject;
    }
}
