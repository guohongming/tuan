package com.demo.tuan.controller;

import com.demo.tuan.common.CommonUtil;
import com.demo.tuan.model.Order;
import com.demo.tuan.model.Shop;
import com.demo.tuan.model.ShopEx;
import com.demo.tuan.model.User;
import com.demo.tuan.repository.OrderRepository;
import com.demo.tuan.repository.ProductRepository;
import com.demo.tuan.repository.ShopRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;
    @Resource
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CommonUtil commonUtil;

    @RequestMapping("/addShop")
    public @ResponseBody  String addShop(HttpServletRequest request, Long productId){
        String token = "";
        Shop shop = new Shop();
        shop.setProductId(productId);
        shop.setProductNum(1);
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
            shop.setUserId(user.getId());
        }
        shopRepository.save(shop);
        return "success";
    }

    @RequestMapping("/delShop")
    public @ResponseBody  String deleteShop(HttpServletRequest request, Long id){
        shopRepository.deleteById(id);
        return "success";
    }

    @RequestMapping("/shopAll")
    public @ResponseBody  String shopAll(HttpServletRequest request){
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
        Long userId = user.getId();
        List<Shop> shopList = shopRepository.queryAllByUserIdEquals(user.getId());
        List<ShopEx> exList=new ArrayList<>();
        for (Shop shop : shopList) {
            ShopEx ex = new ShopEx();
            BeanUtils.copyProperties(shop, ex);
            ex.setProductName( productRepository.findById(shop.getProductId()).get().getProductName());
            exList.add(ex);
        }
        for (ShopEx ex : exList) {
            Order order = new Order();
            order.setStatus(1);
            order.setProductId(ex.getProductId());
            order.setProductName(ex.getProductName());
            order.setUserId(userId);
            order.setCreateTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss ", Locale.ENGLISH));
            orderRepository.save(order);
            shopRepository.deleteById(ex.getId());
        }

        return "success";
    }

    @RequestMapping("/shopList")
    public ModelAndView shopList(HttpServletRequest request){
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
        List<Shop> shopList = shopRepository.queryAllByUserIdEquals(user.getId());
        List<ShopEx> exList=new ArrayList<>();
        for (Shop shop : shopList) {
            ShopEx ex = new ShopEx();
             BeanUtils.copyProperties(shop, ex);
             ex.setProductName( productRepository.findById(shop.getProductId()).get().getProductName());
             exList.add(ex);
        }
        map.put("shopList", exList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/shop");
        modelAndView.addAllObjects(map);
        return modelAndView;
    }
}
