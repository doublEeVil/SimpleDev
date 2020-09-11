package com.simpledev.springboot_mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document
@Getter
@Setter
public class LimitUser {
    @Id
    private int id;

    private String name;
    private String address;
    private int age;
    @BsonProperty
    CommObj info;
}
