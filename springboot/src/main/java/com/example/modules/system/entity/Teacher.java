package com.example.modules.system.entity;

import lombok.Data;

@Data
public class Teacher {

    private Long id;

    private Long userId;

    private String teacherNo;

    private String gender; // MALE / FEMALE

    private String title; // 职称，例如讲师、副教授

}
