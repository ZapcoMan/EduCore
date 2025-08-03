package com.example.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应封装类
 * @param <T> 泛型类型，响应数据的类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "统一响应封装类")
public class R<T> {

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "响应状态码")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "响应数据（键值对形式）")
    private Map<String, Object> dataMap = new HashMap<>();

    private R() {}

    /**
     * 无数据成功响应
     */
    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }
    /**
     * 无数据成功响应
     */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 带数据成功响应
     */
    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage("请求成功");
        r.setData(data);
        return r;
    }

    /**
     * 默认错误响应
     */
    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    /**
     * 自定义错误响应（带状态码和消息）
     */
    public static <T> R<T> error(Integer code, String msg) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    /**
     * 自定义错误响应（仅消息）
     */
    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(msg);
        return r;
    }

    /**
     * 根据枚举结果创建响应
     */
    public static <T> R<T> setResult(ResultCodeEnum result) {
        R<T> r = new R<>();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    // 链式调用

    public R<T> success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public R<T> data(T data) {
        this.setData(data);
        this.dataMap.clear();
        return this;
    }

    public R<T> data(String key, Object value) {
        this.setData(null);
        this.dataMap.put(key, value);
        return this;
    }

    public R<T> data(Map<String, Object> map) {
        this.setData(null);
        this.dataMap = map;
        return this;
    }
}
