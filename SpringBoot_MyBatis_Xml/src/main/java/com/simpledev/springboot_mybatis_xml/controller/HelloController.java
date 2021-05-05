package com.simpledev.springboot_mybatis_xml.controller;

import com.simpledev.springboot_mybatis_xml.entity.User;
import com.simpledev.springboot_mybatis_xml.mapper.UserMapper;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;

@RestController
public class HelloController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplicationContext context;

    @RequestMapping("/hello")
    public String hello() throws Exception {
        Resource resource = new ClassPathResource("mapper/UserMapper.xml");
        File file = resource.getFile();
        List<String> all= Files.readAllLines(resource.getFile().toPath());
        all.forEach(System.out::println);
        System.out.println(file.exists());
        //---
        Configuration configuration = new Configuration();
        Properties p = new Properties();
        Resource jdbcCfg = new ClassPathResource("application.properties");
        p.load(jdbcCfg.getInputStream());
        configuration.setVariables(p);
        XMLMapperBuilder xmlMapperBuilder
                = new XMLMapperBuilder(resource.getInputStream(), configuration,
                String.valueOf(resource), configuration.getSqlFragments());
        xmlMapperBuilder.parse();
        ErrorContext.instance().reset();
        //--

        return "+++";
    }

    @RequestMapping("/q")
    public List<User> select() {
        List<User> userList = userMapper.findAN();
        return userList;
    }

}
