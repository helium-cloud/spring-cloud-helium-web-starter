package com.coral.mini.program.common.config.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.coral.mini.program.common.business.serviceimpl.AbstractInitSourceService;
import com.coral.mini.program.common.common.constant.SecurityConstant;
import com.coral.mini.program.common.config.AuthConfig;
import com.coral.mini.program.common.utils.HttpUtils;
import com.mysql.jdbc.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 第三方token认证
 * @author coral
 */

@Component
public class AuthenticationExtFilter {


    private static final Logger log = LoggerFactory.getLogger(AuthenticationExtFilter.class);

    @Autowired
    private AuthConfig authConfig;

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @return
     */
    public UsernamePasswordAuthenticationToken extAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain){

        String extToken = HttpUtils.getValue(request, SecurityConstant.EXT_HEADER);
        log.info("extAuthentication:{} -{}:{} ", request.getRequestURI(), SecurityConstant.EXT_HEADER, extToken);
        if (StringUtils.isNullOrEmpty(extToken) || "null".equalsIgnoreCase(extToken)){
            return null;
        }
        //第三方认证
        String user = getOpenId(extToken);
        if (StringUtils.isNullOrEmpty(user) || "null".equalsIgnoreCase(user)){
            return null;
        }
        //获取用户信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        User principal = new User(user, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public String getOpenId(String extToken){
        if (!authConfig.isExtAuthEnable()){
            return UUID.randomUUID().toString();
        }
        try {
            String content = HttpUtils.getContent(authConfig.getExtAuthUrl() + "?token=" + extToken);
            JSONObject jsonObject = JSONObject.parseObject(content);
            int code = jsonObject.getInteger("code");
            if (code == 200){
                String user = jsonObject.getString("user");
                if (StringUtils.isNullOrEmpty(user)){
                    user = extToken;
                }
                return user;
            }

        } catch (Exception e){
            log.error("get user exception:{}", extToken, e);
        }
        return null;
    }

    /**
     * {
     *     "code": 200,
     *     "data": {
     *         "operator": "1",
     *         "result": 103000,
     *         "nickName": "15001396923",
     *         "openId": "1552394261797",
     *         "sessionId": "734f1384-af8f-4839-96b0-8568f0cd4328",
     *         "email": "",
     *         "mobileName": "15001396923"
     *     },
     *     "msg": "OK"
     * }
     */
}
