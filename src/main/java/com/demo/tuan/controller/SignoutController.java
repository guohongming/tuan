package com.demo.tuan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignoutController {

    @RequestMapping("/signout")
    public String signout(HttpServletResponse response){
        Cookie cookie = new Cookie("token","");
        response.addCookie(cookie);
        return "redirect:index";
    }
}
