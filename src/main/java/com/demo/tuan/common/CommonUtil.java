package com.demo.tuan.common;

import com.demo.tuan.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommonUtil {
    private Map<String, User> userMap = new ConcurrentHashMap<>();

    public void addUser(String token,User user){
        userMap.put(token, user);
    }

    public User getUserByToken(String token){
        return userMap.get(token);
    }

}
