package com.example.modules.system.service;


import com.example.core.service.BaseService;
import com.example.modules.system.entity.Course;

import java.util.List;

public interface CourseService extends BaseService<Course, Long> {
    Course getById(Long id);
    List<Course> list();
    void create(Course c);
    void update(Course c);
    void delete(Long id);
}

