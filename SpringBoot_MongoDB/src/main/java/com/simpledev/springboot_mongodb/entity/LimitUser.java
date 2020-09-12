package com.simpledev.springboot_mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.concurrent.ConcurrentHashMap;

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
    private CommObj info;

    /**
     * 即便是有transient，但同样也会写入数据库, 必须使用注解才行=
     */
    @Transient
    private transient String name2;

    private ConcurrentHashMap map;
}
