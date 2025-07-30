package com.example.modules.system.service.impl;


import com.example.modules.system.entity.Student;
import com.example.modules.system.mapper.StudentMapper;
import com.example.modules.system.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student getById(Long id) {
        return studentMapper.findById(id);
    }

    @Override
    public List<Student> list() {
        return studentMapper.findAll();
    }

    @Override
    public void create(Student student) {
        studentMapper.insert(student);
    }

    @Override
    public void update(Student student) {
        studentMapper.update(student);
    }

    @Override
    public void delete(Long id) {
        studentMapper.deleteById(id);
    }

    @Override
    public Student getByUserId(Long userId) {
        return studentMapper.findByUserId(userId);
    }
}