package com.example.modules.system.service;


import com.example.modules.system.entity.Course;

import java.util.List;

public interface CourseService {
    Course getById(Long id);
    List<Course> list();
    void create(Course c);
    void update(Course c);
    void delete(Long id);
}

