package com.example.modules.system.service;


import com.example.core.service.BaseService;
import com.example.modules.system.entity.Student;

import java.util.List;

public interface StudentService extends BaseService<Student, Long> {
    Student getById(Long id);

    List<Student> list();

    void create(Student student);

    void update(Student student);

    void delete(Long id);

    Student getByUserId(Long userId);
}