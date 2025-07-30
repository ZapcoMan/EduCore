package com.example.modules.system.service.impl;


import com.example.modules.system.entity.Teacher;
import com.example.modules.system.mapper.TeacherMapper;
import com.example.modules.system.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;



    @Override
    public Teacher getById(Long id) {
        return teacherMapper.findById(id);
    }

    @Override
    public List<Teacher> list() {
        return teacherMapper.findAll();
    }

    @Override
    public void create(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    @Override
    public void delete(Long id) {
        teacherMapper.deleteById(id);
    }

    @Override
    public Teacher getByUserId(Long userId) {
        return null;
    }


}