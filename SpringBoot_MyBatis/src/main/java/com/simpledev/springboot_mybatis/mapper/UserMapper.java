package com.simpledev.springboot_mybatis.mapper;

import com.simpledev.springboot_mybatis.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM User WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO User(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Delete("DELETE FROM User")
    int deleteAll();

    @Insert("INSERT INTO User(name,age) values(#{name, jdbcType=VARCHAR}, #{age, jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Insert("INSERT INTO User(name,age) values(#{name}, #{age})")
    int insertByUser(User user);

    @Update("UPDATE User SET age=#{age} WHERE name = #{name}")
    void updateAgeByName(@Param("age") int age,@Param("name") String name);

    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    @Select("SELECT name,age FROM User")
    List<User> getAll();

    // 分页方式1 limit a, b本质等于取范围[a+1, b+a+1]
    @Select("SELECT name,age FROM User limit #{curIndex},#{page}")
    List<User> getAllByPage(@Param("curIndex")int curIndex, @Param("page")int page);

    // 分页方式2 采用插件

    // 分页方式3 采用bold

    // 分页方式4， 采用流的方式
}
