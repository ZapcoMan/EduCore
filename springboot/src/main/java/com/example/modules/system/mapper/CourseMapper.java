package com.example.modules.system.mapper;


import com.example.core.mapper.BaseMapper;
import com.example.modules.system.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CourseMapper接口用于定义与Course实体相关的数据库操作
 * 它是一个标记接口，通过@Mapper注解指示MyBatis该接口是一个映射器
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course, Long> {

    /**
     * 根据课程ID查找课程
     *
     * @param id 课程的唯一标识符
     * @return 返回找到的课程对象，如果不存在则返回null
     */
    Course findById(Long id);

    /**
     * 查找所有课程
     *
     * @return 返回一个包含所有课程的列表
     */
    List<Course> findAll();

    /**
     * 插入新的课程记录
     *
     * @param course 要插入的课程对象
     */
    void insert(Course course);

    /**
     * 更新课程记录
     *
     * @param course 包含更新数据的课程对象
     * @return 返回更新操作影响的行数
     */
    int update(Course course);

    /**
     * 根据课程ID删除课程记录
     *
     * @param id 课程的唯一标识符
     */
    void deleteById(Long id);
}
