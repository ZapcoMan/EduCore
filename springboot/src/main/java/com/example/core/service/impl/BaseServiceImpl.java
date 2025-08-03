package com.example.core.service.impl;

import com.example.core.service.BaseService;

import java.util.List;

/**
 * 自定义通用 Service 实现类，使用传统 MyBatis Mapper 实现。
 * @param <T> 实体类
 * @param <ID> 主键类型
 * @param <M> Mapper 类型
 */
public abstract class BaseServiceImpl<T, ID, M> implements BaseService<T, ID> {

    protected final M mapper;

    public BaseServiceImpl(M mapper) {
        this.mapper = mapper;
    }

    public abstract T selectById(Long aLong);

    @Override
    public abstract T selectById(ID id);

    @Override
    public abstract List<T> selectAll();

    public abstract void updateById(Long id);

    @Override
    public abstract void insert(T entity);

    @Override
    public abstract void updateById(T entity);

    @Override
    public abstract void deleteById(ID id);
}