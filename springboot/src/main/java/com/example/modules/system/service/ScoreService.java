package com.example.modules.system.service;


import com.example.core.service.BaseService;
import com.example.modules.system.entity.Score;

import java.util.List;

public interface ScoreService  extends BaseService<Score, Long> {
    Score getById(Long id);

    List<Score> list();

    void create(Score score);

    void update(Score score);

    void delete(Long id);

    List<Score> listByStudentId(Long studentId);

    List<Score> listByCourseId(Long courseId);
}