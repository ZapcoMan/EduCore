package com.example.modules.system.entity;

import lombok.Data;

/**
 * 课程类，用于表示系统中的课程信息
 * 该类使用了Lombok的@Data注解，自动生成getter和setter方法，以及toString、equals和hashCode方法
 */
@Data
public class Course {

    /**
     * 课程ID，唯一标识一个课程
     */
    private Long id;

    /**
     * 课程名称，用于显示课程的标题
     */
    private String name;

    /**
     * 教师ID，标识教授该课程的教师
     */
    private Long teacherId;

    /**
     * 课程描述，包含课程的详细信息和说明
     */
    private String description;
}
