package com.simpledev.spring_boot_jpa.service;

import com.simpledev.spring_boot_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findByNameAndAge(String name, Integer age);

    @Query("from User u where u.name=:name")
    List<User> findUser(@Param("name") String name);

    /**
     * 这里直接使用原生sql语句，HQL对limit不支持
     * @param age
     * @return
     */
    @Query(value = "select * from User where age > ? limit 2", nativeQuery = true)
    List<User> findUser(@Param("age") int age);
}
