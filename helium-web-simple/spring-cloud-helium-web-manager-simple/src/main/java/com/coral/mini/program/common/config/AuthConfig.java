package com.coral.mini.program.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "mp.auth")
public class AuthConfig {
    private Boolean tokenRedis = true;

    private Integer tokenExpireTime = 60;

    private Integer saveLoginTime = 7;

    private String extAuthUrl = "http://127.0.0.1:8095/coral/app/mock/auth/check";

    private boolean extAuthEnable = false;

    private Integer loginTimeLimit = 10;

    private Integer loginAfterTime = 10;

    private boolean rateLimitEnable = true;

    private Integer rateLimit = 100;


    private Integer rateTimeout = 1000;

    private static List<String> ignoreUrls = new ArrayList<>();


    public Boolean getTokenRedis() {
        return tokenRedis;
    }

    public void setTokenRedis(Boolean tokenRedis) {
        this.tokenRedis = tokenRedis;
    }

    public Integer getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Integer tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Integer getSaveLoginTime() {
        return saveLoginTime;
    }

    public void setSaveLoginTime(Integer saveLoginTime) {
        this.saveLoginTime = saveLoginTime;
    }

    public String getExtAuthUrl() {
        return extAuthUrl;
    }

    public void setExtAuthUrl(String extAuthUrl) {
        this.extAuthUrl = extAuthUrl;
    }

    public boolean isExtAuthEnable() {
        return extAuthEnable;
    }

    public void setExtAuthEnable(boolean extAuthEnable) {
        this.extAuthEnable = extAuthEnable;
    }

    public Integer getLoginTimeLimit() {
        return loginTimeLimit;
    }

    public void setLoginTimeLimit(Integer loginTimeLimit) {
        this.loginTimeLimit = loginTimeLimit;
    }

    public Integer getLoginAfterTime() {
        return loginAfterTime;
    }

    public void setLoginAfterTime(Integer loginAfterTime) {
        this.loginAfterTime = loginAfterTime;
    }

    public boolean isRateLimitEnable() {
        return rateLimitEnable;
    }

    public void setRateLimitEnable(boolean rateLimitEnable) {
        this.rateLimitEnable = rateLimitEnable;
    }

    public Integer getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
    }

    public Integer getRateTimeout() {
        return rateTimeout;
    }

    public void setRateTimeout(Integer rateTimeout) {
        this.rateTimeout = rateTimeout;
    }

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }
}
