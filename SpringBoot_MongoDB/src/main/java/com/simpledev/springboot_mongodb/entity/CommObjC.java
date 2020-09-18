package com.simpledev.springboot_mongodb.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class CommObjC {
    private Map<String, Object> map = new ConcurrentHashMap<>();
}
