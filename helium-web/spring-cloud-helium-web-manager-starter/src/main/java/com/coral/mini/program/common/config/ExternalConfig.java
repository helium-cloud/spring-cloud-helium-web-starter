package com.coral.mini.program.common.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 对外接口配置
 * @Author: hbHao
 * @Date: 2020/5/28 11:12
 */

@Component
@ConfigurationProperties(prefix = "external.urcs")
public class ExternalConfig {
    private String  urcsQueryOnlineListUrl="http://10.10.12.87:8094/tas/rcs/tas/webrtcsignaling/v1/onLineOneToOne/sessions/getOnLineSession";
    private String  urcsDelOnlineOneToOneUrl="http://10.10.12.87:8094/tas/rcs/tas/webrtcsignaling/v1/onLineOneToOne/sessions/del";
    private String  urcsQueryShowUrl="http://172.16.157.90:8086/rcs/show/";


    private String  sipTokenUrl="http://172.16.106.69:9080/api/v3/auth";

    private String name="admin";

    private String pwd="sipcapture";

    public String getUrcsQueryOnlineListUrl() {
        return urcsQueryOnlineListUrl;
    }

    public void setUrcsQueryOnlineListUrl(String urcsQueryOnlineListUrl) {
        this.urcsQueryOnlineListUrl = urcsQueryOnlineListUrl;
    }

    public String getUrcsDelOnlineOneToOneUrl() {
        return urcsDelOnlineOneToOneUrl;
    }

    public void setUrcsDelOnlineOneToOneUrl(String urcsDelOnlineOneToOneUrl) {
        this.urcsDelOnlineOneToOneUrl = urcsDelOnlineOneToOneUrl;
    }

    public String getUrcsQueryShowUrl() {
        return urcsQueryShowUrl;
    }

    public void setUrcsQueryShowUrl(String urcsQueryShowUrl) {
        this.urcsQueryShowUrl = urcsQueryShowUrl;
    }

    public String getSipTokenUrl() {
        return sipTokenUrl;
    }

    public void setSipTokenUrl(String sipTokenUrl) {
        this.sipTokenUrl = sipTokenUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
