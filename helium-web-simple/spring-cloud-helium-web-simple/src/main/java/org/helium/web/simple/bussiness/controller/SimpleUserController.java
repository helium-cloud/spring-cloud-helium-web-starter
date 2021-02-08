package org.helium.web.simple.bussiness.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.helium.web.common.business.BaseController;
import org.helium.web.common.business.BaseService;
import org.helium.web.common.business.entity.Log;
import org.helium.web.common.business.service.LogService;
import org.helium.web.common.common.utils.PageUtil;
import org.helium.web.common.common.utils.ResultUtil;
import org.helium.web.common.common.vo.PageVo;
import org.helium.web.common.common.vo.Result;
import org.helium.web.common.common.vo.SearchVo;
import org.helium.web.simple.bussiness.entity.SimpleUser;
import org.helium.web.simple.bussiness.service.SimpleUserService;
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
@RequestMapping("/user")
@Transactional
public class SimpleUserController extends BaseController<SimpleUser,String> {

    @Autowired
    private SimpleUserService simpleUserService;

    @Override
    public BaseService getService() {
        return simpleUserService;
    }
}
