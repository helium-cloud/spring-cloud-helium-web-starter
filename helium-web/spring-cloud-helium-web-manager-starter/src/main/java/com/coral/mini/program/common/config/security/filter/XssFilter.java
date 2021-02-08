package com.coral.mini.program.common.config.security.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter {
    public static void doXssFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String origin = request.getHeader("Origin");
        String allowHeaders = request.getHeader("Access-Control-Request-Headers");
        if (request.getMethod().equals("OPTIONS")) {
            if (origin != null) {
                response.addHeader("Access-Control-Allow-Origin", origin);
            } else {
                response.addHeader("Access-Control-Allow-Origin", "*");
            }
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Method", "DELETE, GET, POST, PATCH, PUT");
            if (allowHeaders != null) {
                response.addHeader("Access-Control-Allow-Headers", allowHeaders);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        } else {
            if (origin != null) {
                response.addHeader("Access-Control-Allow-Origin", origin);
            } else {
                response.addHeader("Access-Control-Allow-Origin", "*");
            }
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }
    }
}
