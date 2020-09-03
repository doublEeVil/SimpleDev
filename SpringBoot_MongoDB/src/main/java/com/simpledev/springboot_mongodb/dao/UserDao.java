package com.simpledev.springboot_mongodb.dao;

import com.simpledev.springboot_mongodb.entity.User;

public interface UserDao {
    void save(User user);
    void delete(User user);
    void update(User user);
    User findById(int id);
    User findByName(String name);
    User findByNameAndAge(String name, int age);

    void deleteAll();
}
