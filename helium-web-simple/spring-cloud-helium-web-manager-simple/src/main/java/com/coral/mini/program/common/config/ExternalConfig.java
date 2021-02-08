package com.coral.mini.program.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 对外接口配置
 * @Author: hbHao
 * @Date: 2020/5/28 11:12
 */
@Data
@Component
@ConfigurationProperties(prefix = "external.urcs")
public class ExternalConfig {
    private String  urcsQueryOnlineListUrl="http://10.10.12.87:8094/tas/rcs/tas/webrtcsignaling/v1/onLineOneToOne/sessions/getOnLineSession";
    private String  urcsDelOnlineOneToOneUrl="http://10.10.12.87:8094/tas/rcs/tas/webrtcsignaling/v1/onLineOneToOne/sessions/del";
    private String  urcsQueryShowUrl="http://172.16.157.90:8086/rcs/show/";


    private String  sipTokenUrl="http://172.16.106.69:9080/api/v3/auth";

    private String name="admin";

    private String pwd="sipcapture";


}
