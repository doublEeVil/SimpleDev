package com.simpledev.springboot_mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user") // 集合名, 务必注意是collection 而不是collation
public class User {
    @Id
    private int id;
    private String name;
    private int age;
}
