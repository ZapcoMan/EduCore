package com.example.core.mapper;

import java.util.List;

/**
 * 自定义 BaseMapper 接口
 * @param <T> 实体类
 * @param <ID> 主键类型
 */
public interface BaseMapper<T, ID> {

    T selectById(ID id);

    List<T> selectAll();

    void insert(T entity);

    void updateById(T entity);

    void deleteById(ID id);
}

