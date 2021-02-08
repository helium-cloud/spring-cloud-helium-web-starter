package com.coral.mini.program.common.common.exception;



/**
 * @author coral
 */

public class MpException extends RuntimeException {

    private String msg;

    public MpException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
