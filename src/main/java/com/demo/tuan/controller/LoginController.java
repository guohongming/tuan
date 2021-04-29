package com.demo.tuan.controller;

import com.demo.tuan.common.CommonUtil;
import com.demo.tuan.model.User;
import com.demo.tuan.pojo.LoginUserVO;
import com.demo.tuan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtil commonUtil;

    @RequestMapping("/signin")
    public ModelAndView signin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/signin");
        return modelAndView;
    }

    @RequestMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/signup");
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public
    String login(HttpServletResponse response, @RequestParam String username, String password){
        List<User> user = userRepository.findByName(username);
        if(!CollectionUtils.isEmpty(user)  ){
            if(user.get(0).getPassword().equals(password)){
                String token = UUID.randomUUID().toString();
                commonUtil.addUser(token,user.get(0));
                Cookie cookie = new Cookie("token",token);
                response.addCookie(cookie);
                return "redirect:index";
            }else {
                return "密码错误";
            }
        }else {
            return "错误";
        }
    }

    @RequestMapping(value = "/register")
    public
    String register(HttpServletResponse response, @RequestParam String username, String password){
        User user = new  User();
        user.setUserName(username);
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:signin";
    }

}
