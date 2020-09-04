package com.simpledev.springboot_mongodb_cache.dao;

import com.simpledev.springboot_mongodb_cache.entity.User;

public interface UserDao {
    void save(User user);
    void delete(User user);
    void update(User user);
    User findById(int id);
    User findByName(String name);
    User findByNameAndAge(String name, int age);

    void deleteAll();
}
