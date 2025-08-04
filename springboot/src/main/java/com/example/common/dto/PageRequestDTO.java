package com.example.common.dto;

import lombok.Data;

/**
 * 分页请求参数封装类
 * 用于封装分页查询的请求参数，包括页码、每页记录数、排序字段等信息
 */
@Data
public class PageRequestDTO {
    /**
     * 当前页码，从 1 开始
     */
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;

    /**
     * 排序字段（可选）
     */
    private String orderBy;

    /**
     * 是否升序（true: asc, false: desc）
     */
    private Boolean asc = true;

    /**
     * 计算分页查询的偏移量
     * @return 偏移量，即从第几条记录开始查询
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
