package com.coral.mini.program.common.config.security;

import com.coral.mini.program.common.common.exception.LoginFailLimitException;
import com.coral.mini.program.common.business.entity.User;
import com.coral.mini.program.common.business.service.UserService;

import com.coral.mini.program.common.config.security.permission.MyFilterSecurityInterceptor;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author coral
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService{


    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername:{}", username);
        String flagKey = "loginFailFlag:"+username;
        String value = redisTemplate.opsForValue().get(flagKey);
        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        if(!StringUtil.isNullOrEmpty(value)){
            //超过限制次数
            throw new LoginFailLimitException("登录错误次数超过限制，请"+timeRest+"分钟后再试");
        }
        User user = userService.findByUsername(username);
        return new SecurityUserDetails(user);
    }
}
