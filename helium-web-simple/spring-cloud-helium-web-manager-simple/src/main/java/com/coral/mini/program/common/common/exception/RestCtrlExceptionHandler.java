package com.coral.mini.program.common.common.exception;

import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author coral
 */
@Slf4j
@RestControllerAdvice
public class RestCtrlExceptionHandler {

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
