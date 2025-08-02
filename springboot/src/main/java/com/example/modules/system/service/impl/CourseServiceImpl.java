package com.example.modules.system.service.impl;


import com.example.modules.system.entity.Course;
import com.example.modules.system.mapper.CourseMapper;
import com.example.modules.system.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程服务实现类
 * 实现了CourseService接口，用于处理课程相关的业务逻辑
 */
@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseMapper> implements CourseService {
    @Resource
    private CourseMapper mapper;

    public CourseServiceImpl(CourseMapper mapper) {
        super(mapper);
    }

    /**
     * 根据ID获取课程信息
     *
     * @param id 课程ID
     * @return Course 课程信息
     */
    @Override
    public Course getById(Long id) { return mapper.findById(id); }

    /**
     * 获取所有课程列表
     *
     * @return List<Course> 课程列表
     */
    @Override
    public List<Course> list() { return mapper.findAll(); }

    /**
     * 创建新的课程记录
     *
     * @param course 课程信息
     */
    @Override
    public void create(Course course) { mapper.insert(course); }

    /**
     * 更新课程信息
     *
     * @param course 课程信息
     */
    @Override
    public void update(Course course) { mapper.update(course); }

    /**
     * 根据ID删除课程记录
     *
     * @param id 课程ID
     */
    @Override
    public void delete(Long id) { mapper.deleteById(id); }

    /**
     * 根据ID查询课程信息
     *
     * @param id 课程ID
     * @return Course 课程信息
     */
    @Override
    public Course selectById(Long id) {
        return null;
    }

    /**
     * 查询所有课程信息
     *
     * @return List<Course> 课程信息列表
     */
    @Override
    public List<Course> selectAll() {
        return List.of();
    }

    /**
     * 根据ID更新课程信息
     *
     * @param id 课程ID
     */
    @Override
    public void updateById(Long id) {

    }

    /**
     * 插入课程信息
     *
     * @param course 课程信息
     */
    @Override
    public void insert(Course course) {

    }

    /**
     * 根据ID更新课程信息
     *
     * @param course 课程信息
     */
    @Override
    public void updateById(Course course) {

    }

    /**
     * 根据ID删除课程信息
     *
     * @param id 课程ID
     */
    @Override
    public void deleteById(Long id) {

    }
}
