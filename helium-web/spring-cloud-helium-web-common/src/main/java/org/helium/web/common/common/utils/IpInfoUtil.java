package org.helium.web.common.common.utils;



import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * @author coral
 */

@Component
public class IpInfoUtil {

    /**
     * 获取客户端IP地址
     * @param request 请求
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {

        return "";
    }

    /**
     * 获取IP返回地理天气信息
     * @param ip ip地址
     * @return
     */
    public String getIpWeatherInfo(String ip){
        return "";
    }

    /**
     * 获取IP返回地理信息
     * @param ip ip地址
     * @return
     */
    public String getIpCity(String ip){
        return "";
    }

    public void getUrl(HttpServletRequest request){

    }

    public void getInfo(HttpServletRequest request, String p){

    }
}
