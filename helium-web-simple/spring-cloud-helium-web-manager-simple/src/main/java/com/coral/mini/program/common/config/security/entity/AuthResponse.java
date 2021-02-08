package com.coral.mini.program.common.config.security.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * 统一认证网关响应体
 *
 * @author: shaoboyu@hotmail.com
 * @create: 19-3-1
 **/
public class AuthResponse {

    private int code;

    private String msg;

    private JSONObject data;

    private String reason;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AuthResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
