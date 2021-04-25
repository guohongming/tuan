package com.demo.tuan.repository;


import com.demo.tuan.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> queryAllByUserIdEquals(Long userId);

    void deleteAllByUserIdEquals(Long userId);
}
