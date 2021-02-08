package com.coral.mini.program.common.config.security.jwt;

import com.coral.mini.program.common.common.annotation.SystemLog;
import com.coral.mini.program.common.common.constant.SecurityConstant;
import com.coral.mini.program.common.common.enums.LogType;
import com.coral.mini.program.common.common.utils.IpInfoUtil;
import com.coral.mini.program.common.common.utils.ResponseUtil;
import com.coral.mini.program.common.common.vo.TokenUser;
import com.coral.mini.program.common.config.AuthConfig;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理类
 * @author coral
 */

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @SystemLog(description = "登录系统", type = LogType.LOGIN)
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        long tokenExpireTime = 0;
        //用户选择保存登录状态几天
        String saveLogin = request.getParameter(SecurityConstant.SAVE_LOGIN);
        Boolean saved = false;
        if(!StringUtil.isNullOrEmpty(saveLogin) && Boolean.valueOf(saveLogin)){
            saved = true;
            if(!authConfig.getTokenRedis()){
                tokenExpireTime = authConfig.getTokenExpireTime() * 60 * 24;
            }
        }
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        List<String> list = new ArrayList<>();
        for(GrantedAuthority g : authorities){
            list.add(g.getAuthority());
        }
        ipInfoUtil.getUrl(request);
        // 登陆成功生成token
        String token;
        if(authConfig.getTokenRedis()){
            // redis
            token = UUID.randomUUID().toString().replace("-", "");
            TokenUser user = new TokenUser(username, list, saved);
            // 单点登录 之前的token失效
            String oldToken = redisTemplate.opsForValue().get(SecurityConstant.USER_TOKEN + username);
            if(!StringUtil.isNullOrEmpty(oldToken)){
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + oldToken);
            }
            if(saved){
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, authConfig.getSaveLoginTime(), TimeUnit.DAYS);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), authConfig.getSaveLoginTime(), TimeUnit.DAYS);
            }else{
                redisTemplate.opsForValue().set(SecurityConstant.USER_TOKEN + username, token, tokenExpireTime, TimeUnit.MINUTES);
                redisTemplate.opsForValue().set(SecurityConstant.TOKEN_PRE + token, new Gson().toJson(user), tokenExpireTime, TimeUnit.MINUTES);
            }
        }else{
            // jwt
            token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(username)
                    //自定义属性 放入用户拥有请求权限
                    .claim(SecurityConstant.AUTHORITIES, new Gson().toJson(list))
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                    .compact();
        }

        ResponseUtil.out(response, ResponseUtil.resultMap(true,200,"登录成功", token));
    }
}
