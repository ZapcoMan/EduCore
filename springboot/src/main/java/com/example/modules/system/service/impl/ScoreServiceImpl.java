package com.example.modules.system.service.impl;


import com.example.modules.system.entity.Score;
import com.example.modules.system.mapper.ScoreMapper;
import com.example.modules.system.service.ScoreService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public Score getById(Long id) {
        return scoreMapper.findById(id);
    }

    @Override
    public List<Score> list() {
        return scoreMapper.findAll();
    }

    @Override
    public void create(Score score) {
        scoreMapper.insert(score);
    }

    @Override
    public void update(Score score) {
        scoreMapper.update(score);
    }

    @Override
    public void delete(Long id) {
        scoreMapper.deleteById(id);
    }

    @Override
    public List<Score> listByStudentId(Long studentId) {
        return scoreMapper.findByStudentId(studentId);
    }

    @Override
    public List<Score> listByCourseId(Long courseId) {
        return scoreMapper.findByCourseId(courseId);
    }
}