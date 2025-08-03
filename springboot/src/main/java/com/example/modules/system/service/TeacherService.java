package com.example.modules.system.service;


import com.example.core.service.BaseService;
import com.example.modules.system.entity.Teacher;

import java.util.List;

public interface TeacherService extends BaseService<Teacher, Long> {
    Teacher getById(Long id);

    List<Teacher> list();

    void create(Teacher teacher);

    void update(Teacher teacher);

    void delete(Long id);

    Teacher getByUserId(Long userId);


}