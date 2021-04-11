package com.demo.tuan.controller;

import com.demo.tuan.common.CommonUtil;
import com.demo.tuan.model.Product;
import com.demo.tuan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DetailController {

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request, @RequestParam("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/detail");
        Map<String,Object> map = new HashMap<>();
        Cookie[] cookies =  request.getCookies();
        String token = "";
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                }
            }
        }
        Product product = productRepository.findById(id).get();
        map.put("userInfo", commonUtil.getUserByToken(token));
        map.put("product", product);
        modelAndView.addAllObjects(map);
        return modelAndView;

    }
}
