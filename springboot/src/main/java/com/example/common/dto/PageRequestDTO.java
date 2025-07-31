package com.example.common.dto;

import lombok.Data;

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

    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
