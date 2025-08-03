package com.example.modules.system.service.impl;


import com.example.core.service.impl.BaseServiceImpl;
import com.example.modules.system.entity.Score;
import com.example.modules.system.mapper.ScoreMapper;
import com.example.modules.system.service.ScoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成绩服务实现类
 * 实现了ScoreService接口，用于处理成绩相关的业务逻辑
 */
@Service
public class ScoreServiceImpl extends BaseServiceImpl<Score, Long, ScoreMapper> implements ScoreService   {

    @Resource
    private ScoreMapper scoreMapper;

    public ScoreServiceImpl(ScoreMapper mapper) {
        super(mapper);
    }

    /**
     * 根据ID获取成绩信息
     *
     * @param id 成绩ID
     * @return Score 成绩信息
     */
    @Override
    public Score getById(Long id) {
        return scoreMapper.findById(id);
    }

    /**
     * 获取所有成绩列表
     *
     * @return List<Score> 成绩列表
     */
    @Override
    public List<Score> list() {
        return scoreMapper.findAll();
    }

    /**
     * 创建新的成绩记录
     *
     * @param score 成绩信息
     */
    @Override
    public void create(Score score) {
        scoreMapper.insert(score);
    }

    /**
     * 更新成绩信息
     *
     * @param score 成绩信息
     */
    @Override
    public void update(Score score) {
        scoreMapper.update(score);
    }

    /**
     * 根据ID删除成绩记录
     *
     * @param id 成绩ID
     */
    @Override
    public void delete(Long id) {
        scoreMapper.deleteById(id);
    }

    /**
     * 根据学生ID获取成绩列表
     *
     * @param studentId 学生ID
     * @return List<Score> 该学生的所有成绩列表
     */
    @Override
    public List<Score> listByStudentId(Long studentId) {
        return scoreMapper.findByStudentId(studentId);
    }

    /**
     * 根据课程ID获取成绩列表
     *
     * @param courseId 课程ID
     * @return List<Score> 该课程的所有成绩列表
     */
    @Override
    public List<Score> listByCourseId(Long courseId) {
        return scoreMapper.findByCourseId(courseId);
    }

    /**
     * 根据ID查询成绩信息
     *
     * @param id 成绩ID
     * @return Score 成绩信息
     */
    @Override
    public Score selectById(Long id) {
        return scoreMapper.findById(id);
    }

    /**
     * 查询所有成绩信息
     *
     * @return List<Score> 成绩信息列表
     */
    @Override
    public List<Score> selectAll() {
        return List.of();
    }

    /**
     * @param id
     */
    @Override
    public void updateById(Long id) {
        scoreMapper.updateById(id);
    }

    /**
     * 插入成绩信息
     *
     * @param score 成绩信息
     */
    @Override
    public void insert(Score score) {
        scoreMapper.insert(score);
    }

    /**
     * 根据ID更新成绩信息
     *
     * @param score 成绩信息
     */
    @Override
    public void updateById(Score score) {
        scoreMapper.updateById(score);
    }

    /**
     * 根据ID删除成绩信息
     *
     * @param id 成绩ID
     */
    @Override
    public void deleteById(Long id) {
        scoreMapper.deleteById(id);
    }
}
