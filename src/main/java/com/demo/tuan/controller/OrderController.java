package com.demo.tuan.controller;

import com.demo.tuan.common.CommonUtil;
import com.demo.tuan.model.Order;
import com.demo.tuan.model.Product;
import com.demo.tuan.model.User;
import com.demo.tuan.repository.OrderRepository;
import com.demo.tuan.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping("/addOrder")
    public @ResponseBody
    String addOrder(HttpServletRequest request, @RequestParam("productId") Long productId) {
        String token = "";
        Map<String, Object> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
            User user = commonUtil.getUserByToken(token);
            if (user == null) {
                return "toLogin";
            }
            Product product = productRepository.findById(productId).get();
            Order order = new Order();
            order.setUserId(user.getId());
            order.setProductId(productId);
            order.setProductName(product.getProductName());
            order.setStatus(0);
            order.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss ", Locale.ENGLISH));
            orderRepository.save(order);
            orderRepository.flush();
            return "success";
        }
        return "toLogin";
    }

    @RequestMapping("/order")
    public ModelAndView order(HttpServletRequest request) {
        String token = "";
        Map<String, Object> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        User user = commonUtil.getUserByToken(token);
        if (user == null) {
            return new ModelAndView("/signin");
        }
        map.put("userInfo", user);
        map.put("orders", orderRepository.queryAllByUserIdEquals(user.getId()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/order");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }


}
