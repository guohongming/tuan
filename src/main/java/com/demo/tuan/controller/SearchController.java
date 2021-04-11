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
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/search")
    public ModelAndView cat(HttpServletRequest request, @RequestParam("key") String key) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/search");
        Map<String, Object> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        String token = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        List<Product> products = productRepository.queryAllByProductNameLike("%"+key+"%");
        map.put("userInfo", commonUtil.getUserByToken(token));
        map.put("products", products);
        map.put("key", key);
        modelAndView.addAllObjects(map);
        return modelAndView;
    }

}
