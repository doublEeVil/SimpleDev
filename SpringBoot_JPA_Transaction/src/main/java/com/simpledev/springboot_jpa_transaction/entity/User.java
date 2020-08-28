package com.simpledev.springboot_jpa_transaction.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;

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
    @Max(50)
    @NonNull
    private int age;

}
