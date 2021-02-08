package com.coral.mini.program.common.common.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author coral
 */
public class Captcha implements Serializable{

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "验证码")
    private String code;

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
