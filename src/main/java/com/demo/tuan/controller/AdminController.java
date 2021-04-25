package com.demo.tuan.controller;

import com.demo.tuan.common.CommonUtil;
import com.demo.tuan.model.Product;
import com.demo.tuan.repository.OrderRepository;
import com.demo.tuan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping("/admin")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin");
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
        map.put("userInfo", commonUtil.getUserByToken(token));

        Iterable<Product> productList =  productRepository.findAll();
        map.put("productList", productList);
        map.put("orderList", orderRepository.findAll());
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
    @RequestMapping("/addProduct")
    public @ResponseBody String addProduct(HttpServletRequest request, @RequestBody Product product){
//        product.setId(productRepository.findLastId()+1L);
        productRepository.saveAndFlush(product);
        return "success";
    }

    @RequestMapping("/delProduct")
    public @ResponseBody String delProduct(HttpServletRequest request, @RequestParam("id") Long id){
        productRepository.deleteById(id);
        return "success";
    }
    @RequestMapping("/delOrder")
    public @ResponseBody String delOrder(HttpServletRequest request, @RequestParam("id") Long id){
        orderRepository.deleteById(id);
        return "success";
    }
}
