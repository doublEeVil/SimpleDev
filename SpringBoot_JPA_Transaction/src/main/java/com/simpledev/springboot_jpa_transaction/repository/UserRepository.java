package com.simpledev.springboot_jpa_transaction.repository;

import com.simpledev.springboot_jpa_transaction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);


}
