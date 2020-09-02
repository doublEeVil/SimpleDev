package com.simpledev.springboot_cache_ehcache.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String name;
    @NonNull
    private int age;


}
