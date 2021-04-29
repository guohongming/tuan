package com.demo.tuan.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoExt {

    public static Map<Long, List<String>> map = new HashMap<>();

    static {
        map.put(1L, Arrays.asList("面积:20平方","最多2人入住","双床","电视，空调"));
        map.put(2L, Arrays.asList("面积:15平方","最多1人入住","单床","电视，空调,洗衣机"));
        map.put(3L, Arrays.asList("面积:30平方","最多2人入住","双床","电视，空调"));
        map.put(4L, Arrays.asList("面积:35平方","最多3人入住","三床","电视，空调"));
        map.put(5L, Arrays.asList("2-3人餐","川菜","免费wifi","热门评论：非常好吃！"));
        map.put(6L, Arrays.asList("3-4人餐","浙江菜","酒水免费","热门评论：环境好，适合约会！"));
        map.put(7L, Arrays.asList("1-2人餐","淮扬菜","米饭可加","热门评论：味道好！"));
        map.put(8L, Arrays.asList("2-3人餐","本帮菜","热水自取","热门评论：服务员很nice！"));



    }

    public static List<String> getInfos(Long id){
        return map.get(id);
    }
}
