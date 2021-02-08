package com.coral.mini.program.common.config.security.jwt;


import com.coral.mini.program.common.common.utils.ResponseUtil;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author coral
 */
@Component

public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ResponseUtil.out(response,ResponseUtil.resultMap(false,403,"抱歉，您没有访问权限"));
    }

}
