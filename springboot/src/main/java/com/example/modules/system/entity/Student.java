package com.example.modules.system.entity;

import lombok.Data;

@Data
public class Student {

    private Long id;

    private Long userId;

    private String studentNo;

    private String gender; // MALE / FEMALE

    private Integer age;

    private String className;

}

