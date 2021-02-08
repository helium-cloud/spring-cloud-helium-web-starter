package org.helium.web.simple.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 设定访问API doc的路由
 */
@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String index() {
        return "redirect:swagger-ui.html";
    }
}