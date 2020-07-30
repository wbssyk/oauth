package com.shi.server.authserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shiyakun
 * @Description TODO
 */
@Controller
public class LoginController {


    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("loginProcessUrl", "/test/login");
        return "base-login";
    }

    @ResponseBody
    @GetMapping("/detail")
    public String userDetail() {
        return "7777";
    }


}
