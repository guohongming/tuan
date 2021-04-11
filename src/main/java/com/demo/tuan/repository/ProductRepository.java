package com.demo.tuan.repository;


import com.demo.tuan.model.Product;
import com.demo.tuan.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.management.Query;
import java.util.List;

@Component
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> queryAllByTypeEquals(String type);

    List<Product> queryAllByProductNameLike(String key);
}
