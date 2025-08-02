package com.example.modules.system.service.impl;


import com.example.core.service.impl.BaseServiceImpl;
import com.example.modules.system.entity.Student;
import com.example.modules.system.mapper.StudentMapper;
import com.example.modules.system.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentMapper> implements StudentService {


    @Resource
    private StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper mapper) {
        super(mapper);
    }

    /**
     * 根据学生ID获取学生信息
     *
     * @param id 学生的唯一标识符
     * @return 对应ID的学生对象，如果找不到则返回null
     */
    @Override
    public Student getById(Long id) {
        return studentMapper.findById(id);
    }

    /**
     * 获取所有学生信息列表
     *
     * @return 包含所有学生对象的列表
     */
    @Override
    public List<Student> list() {
        return studentMapper.findAll();
    }

    /**
     * 创建新的学生记录
     *
     * @param student 要创建的学生对象
     */
    @Override
    public void create(Student student) {
        studentMapper.insert(student);
    }

    /**
     * 更新学生记录
     *
     * @param student 要更新的学生对象
     */
    @Override
    public void update(Student student) {
        studentMapper.update(student);
    }

    /**
     * 根据学生ID删除学生记录
     *
     * @param id 要删除的学生的唯一标识符
     */
    @Override
    public void delete(Long id) {
        studentMapper.deleteById(id);
    }

    /**
     * 根据用户ID获取学生信息
     *
     * @param userId 用户的唯一标识符
     * @return 对应用户ID的学生对象，如果找不到则返回null
     */
    @Override
    public Student getByUserId(Long userId) {
        return studentMapper.findByUserId(userId);
    }


    /**
     * 根据学生ID查询学生信息
     *
     * @param id 学生ID
     * @return 学生对象，当前实现返回null
     */
    @Override
    public Student selectById(Long id) {
        return studentMapper.selectById(id);
    }

    /**
     * 查询所有学生信息
     *
     * @return 学生列表，当前实现返回空列表
     */
    @Override
    public List<Student> selectAll() {
        return List.of();
    }

    /**
     * @param id
     */
    @Override
    public void updateById(Long id) {
        studentMapper.updateById(id);
    }

    /**
     * 插入学生信息
     *
     * @param student 学生实体对象
     */
    @Override
    public void insert(Student student) {
        studentMapper.insert(student);
    }

    /**
     * 根据ID更新学生信息
     *
     * @param student 学生实体对象
     */
    @Override
    public void updateById(Student student) {
            studentMapper.updateById(student);
    }

    /**
     * 根据学生ID删除学生信息
     *
     * @param id 学生ID
     */
    @Override
    public void deleteById(Long id) {
        studentMapper.deleteById(id);
    }


}