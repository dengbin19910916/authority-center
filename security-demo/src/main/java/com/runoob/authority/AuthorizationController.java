package com.runoob.authority;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author DENGBIN
 * @since 2018-3-26
 */
@Controller
public class AuthorizationController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
