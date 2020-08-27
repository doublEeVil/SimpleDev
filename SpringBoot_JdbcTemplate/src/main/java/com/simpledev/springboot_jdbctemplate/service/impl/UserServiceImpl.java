package com.simpledev.springboot_jdbctemplate.service.impl;

import com.simpledev.springboot_jdbctemplate.entity.User;
import com.simpledev.springboot_jdbctemplate.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private JdbcTemplate jdbcTemplate;

    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(String name, Integer age) {
        return jdbcTemplate.update("INSERT INTO User(name,age) VALUES(?,?)", name, age);
    }

    @Override
    public List<User> queryByName(String name) {
        List<User> users = jdbcTemplate.query("SELECT id,name,age from User where name=?", ((resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            return user;
        }), name);
        return users;
    }

    @Override
    public int updateNameById(int id, String name) {
        return jdbcTemplate.update("UPDATE User SET name=? WHERE id=?", name, id);
    }


    @Override
    public int deleteByName(String name) {
        return jdbcTemplate.update("DELETE FROM User WHERE name=?" + name);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM User");
    }
}
