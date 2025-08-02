package com.example.modules.system.service.impl;


import com.example.core.service.impl.BaseServiceImpl;
import com.example.modules.system.entity.Teacher;
import com.example.modules.system.mapper.TeacherMapper;
import com.example.modules.system.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 教师服务实现类
 * 实现了TeacherService接口，用于处理教师相关的业务逻辑
 */
@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Long, TeacherMapper> implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherMapper mapper) {
        super(mapper);
    }


    /**
     * 根据ID获取教师信息
     *
     * @param id 教师ID
     * @return Teacher 教师信息
     */
    @Override
    public Teacher getById(Long id) {
        return teacherMapper.findById(id);
    }

    /**
     * 获取所有教师列表
     *
     * @return List<Teacher> 教师列表
     */
    @Override
    public List<Teacher> list() {
        return teacherMapper.findAll();
    }

    /**
     * 创建新的教师记录
     *
     * @param teacher 教师信息
     */
    @Override
    public void create(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    /**
     * 更新教师信息
     *
     * @param teacher 教师信息
     */
    @Override
    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    /**
     * 根据ID删除教师记录
     *
     * @param id 教师ID
     */
    @Override
    public void delete(Long id) {
        teacherMapper.deleteById(id);
    }

    /**
     * 根据用户ID获取教师信息
     *
     * @param userId 用户ID
     * @return Teacher 教师信息
     */
    @Override
    public Teacher getByUserId(Long userId) {
        return null;
    }


    /**
     * 根据ID查询教师信息
     *
     * @param id 教师ID
     * @return Teacher 教师信息
     */
    @Override
    public Teacher selectById(Long id) {
        return teacherMapper.selectById(id);
    }

    /**
     * 查询所有教师信息
     *
     * @return List<Teacher> 教师信息列表
     */
    @Override
    public List<Teacher> selectAll() {
        return List.of();
    }

    /**
     * 根据ID更新教师信息
     *
     * @param id 教师ID
     */
    @Override
    public void updateById(Long id) {
        teacherMapper.updateById(id);
    }

    /**
     * 插入教师信息
     *
     * @param teacher 教师信息
     */
    @Override
    public void insert(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    /**
     * 根据ID更新教师信息
     *
     * @param teacher 教师信息
     */
    @Override
    public void updateById(Teacher teacher) {
        teacherMapper.updateById(teacher);
    }

    /**
     * 根据ID删除教师信息
     *
     * @param id 教师ID
     */
    @Override
    public void deleteById(Long id) {
        teacherMapper.deleteById(id);
    }
}
