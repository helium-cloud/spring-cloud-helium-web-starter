package com.coral.mini.program.common.config.interceptor;

import com.coral.mini.program.common.config.AuthConfig;
import com.coral.mini.program.common.config.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author coral
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private LimitRaterInterceptor limitRaterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(limitRaterInterceptor);
        // 配置拦截的路径
        ir.addPathPatterns("/**");
        // 配置不拦截的路径
        ir.excludePathPatterns(authConfig.getIgnoreUrls());
    }
}
