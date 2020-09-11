package com.simpledev.springboot_mongodb.dao;

import com.simpledev.springboot_mongodb.entity.LimitUser;

public interface LimitUserDao {
    void deleteAll();
    void save(LimitUser user);
    LimitUser find(int id);
}
