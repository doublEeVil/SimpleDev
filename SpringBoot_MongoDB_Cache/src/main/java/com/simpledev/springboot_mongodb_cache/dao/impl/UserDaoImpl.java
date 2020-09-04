package com.simpledev.springboot_mongodb_cache.dao.impl;

import com.simpledev.springboot_mongodb_cache.dao.UserDao;
import com.simpledev.springboot_mongodb_cache.entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(User user) {
        mongoTemplate.save(user);
    }

    @Override
    public void delete(User id) {
        mongoTemplate.remove(id);
    }

    @Override
    public void update(User user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public User findById(int id) {
        return mongoTemplate.findById(id, User.class);
    }

    @Override
    public User findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User findByNameAndAge(String name, int age) {
        Query query = new Query(Criteria.where("name").is(name).and("age").is(age));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.remove(User.class);
    }
}
