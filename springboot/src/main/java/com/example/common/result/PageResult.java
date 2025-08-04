package com.example.common.result;

import lombok.Data;

import java.util.List;

/**
 * 分页结果封装类
 * 用于封装分页查询的结果数据，包括当前页信息、总记录数和数据列表
 * @param <T> 泛型类型，数据列表中元素的类型
 */
@Data
public class PageResult<T> {
    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 返回数据列表
     */
    private List<T> records;

    /**
     * 创建分页结果对象的静态方法
     * @param records 数据列表
     * @param total 总记录数
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param <T> 泛型类型
     * @return PageResult<T> 分页结果对象
     */
    public static <T> PageResult<T> of(List<T> records, Long total, Integer pageNum, Integer pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setRecords(records);
        result.setTotal(total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        return result;
    }
}
