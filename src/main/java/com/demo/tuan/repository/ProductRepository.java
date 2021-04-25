package com.demo.tuan.repository;


import com.demo.tuan.model.Product;
import com.demo.tuan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> queryAllByTypeEquals(String type);

    List<Product> queryAllByProductNameLike(String key);

    @Query(value = "select id from product order by id desc limit 1", nativeQuery = true)
    Integer findLastId();
}
