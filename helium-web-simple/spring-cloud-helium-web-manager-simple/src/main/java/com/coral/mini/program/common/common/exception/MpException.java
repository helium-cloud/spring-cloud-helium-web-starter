package com.coral.mini.program.common.common.exception;

import lombok.Data;

/**
 * @author coral
 */
@Data
public class MpException extends RuntimeException {

    private String msg;

    public MpException(String msg){
        super(msg);
        this.msg = msg;
    }
}
