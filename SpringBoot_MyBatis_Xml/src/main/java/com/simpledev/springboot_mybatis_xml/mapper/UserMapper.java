package com.simpledev.springboot_mybatis_xml.mapper;

import com.simpledev.springboot_mybatis_xml.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User findByName(@Param("name") String name);

    int insert(@Param("name") String name, @Param("age") Integer age);

    int deleteAll();

    int insertByMap(Map<String, Object> map);

    int insertByUser(User user);

    void updateAgeByName(@Param("age") int age,@Param("name") String name);

    List<User> getAll();
}
