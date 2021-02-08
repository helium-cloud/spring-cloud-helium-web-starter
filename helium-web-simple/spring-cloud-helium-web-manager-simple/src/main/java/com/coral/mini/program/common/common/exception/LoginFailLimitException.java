package com.coral.mini.program.common.common.exception;

import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @author coral
 */
@Data
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private String msg;

    public LoginFailLimitException(String msg){
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
