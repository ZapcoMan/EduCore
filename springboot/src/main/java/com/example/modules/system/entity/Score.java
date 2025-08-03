package com.example.modules.system.entity;

import lombok.Data;

/**
 * 成绩类，用于表示学生课程成绩信息
 * 该类使用Lombok的@Data注解，自动生成getter和setter方法，以及toString、equals和hashCode方法
 */
@Data
public class Score {

    /**
     * 成绩记录ID，唯一标识一条成绩记录
     */
    private Long id;

    /**
     * 学生ID，标识成绩所属的学生
     */
    private Long studentId;

    /**
     * 课程ID，标识成绩对应的课程
     */
    private Long courseId;

    /**
     * 分数值，表示学生在指定课程中的得分，通常范围为0-100
     */
    private Double score;
}

