package com.simpledev.springboot_jdbctemplate_druid.service;

import com.simpledev.springboot_jdbctemplate_druid.entity.User;
import java.util.List;

public interface UserService {

    int create(String name, Integer age);

    List<User> queryByName(String name);

    int updateNameById(int id, String name);

    int deleteByName(String name);

    int deleteAll();
}
