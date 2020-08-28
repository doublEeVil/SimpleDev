package com.simpledev.springboot_mybatis_xml.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private int age;

}
