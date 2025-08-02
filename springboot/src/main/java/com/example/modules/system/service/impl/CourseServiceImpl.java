package com.example.modules.system.service.impl;


import com.example.core.service.impl.BaseServiceImpl;
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
    private CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        super(courseMapper);
    }

    /**
     * 根据ID获取课程信息
     *
     * @param id 课程ID
     * @return Course 课程信息
     */
    @Override
    public Course getById(Long id) { return courseMapper.findById(id); }

    /**
     * 获取所有课程列表
     *
     * @return List<Course> 课程列表
     */
    @Override
    public List<Course> list() { return courseMapper.findAll(); }

    /**
     * 创建新的课程记录
     *
     * @param course 课程信息
     */
    @Override
    public void create(Course course) { courseMapper.insert(course); }

    /**
     * 更新课程信息
     *
     * @param course 课程信息
     */
    @Override
    public void update(Course course) { courseMapper.update(course); }

    /**
     * 根据ID删除课程记录
     *
     * @param id 课程ID
     */
    @Override
    public void delete(Long id) { courseMapper.deleteById(id); }

    /**
     * 根据ID查询课程信息
     *
     * @param id 课程ID
     * @return Course 课程信息
     */
    @Override
    public Course selectById(Long id) {
        return courseMapper.selectById( id);
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
        courseMapper.insert(course);
    }

    /**
     * 根据ID更新课程信息
     *
     * @param course 课程信息
     */
    @Override
    public void updateById(Course course) {
        courseMapper.updateById(course);
    }

    /**
     * 根据ID删除课程信息
     *
     * @param id 课程ID
     */
    @Override
    public void deleteById(Long id) {
        courseMapper.deleteById( id);
    }
}
