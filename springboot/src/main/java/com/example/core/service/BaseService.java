package com.example.core.service;

import java.util.List;

/**
 * 自定义基础Service接口，适用于所有实体。
 *
 * @param <T> 实体类
 * @param <ID> 主键类型
 */
public interface BaseService<T, ID> {

    T selectById(ID id);

    List<T> selectAll();

    void insert(T entity);

    void updateById(T entity);

    void deleteById(ID id);
}