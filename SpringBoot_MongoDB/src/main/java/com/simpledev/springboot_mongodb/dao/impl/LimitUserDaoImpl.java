package com.simpledev.springboot_mongodb.dao.impl;

import com.simpledev.springboot_mongodb.dao.LimitUserDao;
import com.simpledev.springboot_mongodb.entity.LimitUser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class LimitUserDaoImpl implements LimitUserDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void deleteAll() {
        mongoTemplate.remove(LimitUser.class);
    }

    @Override
    public void save(LimitUser user) {
        mongoTemplate.save(user);
    }

    @Override
    public LimitUser find(int id) {
        return mongoTemplate.findById(id, LimitUser.class);
    }
}
