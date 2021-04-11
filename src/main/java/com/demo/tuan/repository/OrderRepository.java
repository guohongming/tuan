package com.demo.tuan.repository;

import com.demo.tuan.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {



    List<Order> queryAllByUserIdEquals(Long userId);
}
