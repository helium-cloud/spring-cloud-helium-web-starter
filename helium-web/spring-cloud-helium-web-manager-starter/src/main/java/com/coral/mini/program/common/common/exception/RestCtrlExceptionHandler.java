package com.coral.mini.program.common.common.exception;

import com.coral.mini.program.common.business.serviceimpl.AbstractInitSourceService;
import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.vo.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author coral
 */

@RestControllerAdvice
public class RestCtrlExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    @ExceptionHandler(MpException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<Object> handleXCloudException(MpException e) {

        String errorMsg="coral exception";
        if (e!=null){
            errorMsg=e.getMsg();
            log.error("", e);
        }
        return new ResultUtil<>().setErrorMsg(500, errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<Object> handleException(Exception e) {

        String errorMsg="Exception";
        if (e!=null){
            errorMsg=e.getMessage();
            log.error("", e);
        }
        return new ResultUtil<>().setErrorMsg(500, errorMsg);
    }
}
