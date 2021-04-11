package com.demo.tuan.repository;

import com.demo.tuan.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    @Query(value = "select * from user where user_name = ?1 limit 1", nativeQuery = true)
    List<User> findByName(String name);
}
