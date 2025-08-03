package com.example.core.controller;


import com.example.common.result.R;
import com.example.core.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T, ID> {

    protected final BaseService<T, ID> baseService;

    protected BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    @Operation(summary = "根据ID查询")
    @GetMapping("/{id}")
    public R<T> getById(@PathVariable ID id) {
        return R.success(baseService.selectById(id));
    }

    @Operation(summary = "查询全部")
    @GetMapping
    public R<List<T>> getAll() {
        return R.success(baseService.selectAll());
    }

    @Operation(summary = "新增")
    @PostMapping
    public R<Void> create(@RequestBody T entity) {
        baseService.insert(entity);
        return R.ok();
    }

    @Operation(summary = "更新")
    @PutMapping
    public R<Void> update(@RequestBody T entity) {
        baseService.updateById(entity);
        return R.ok();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable ID id) {
        baseService.deleteById(id);
        return R.ok();
    }
}

