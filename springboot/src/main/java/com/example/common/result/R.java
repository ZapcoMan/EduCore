package com.example.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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

    @Schema(description = "响应 Map 数据")
    private Map<String, Object> dataMap;

    private R() {}

    /** --------- 构造方法 --------- **/

    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = ok();
        r.setData(data);
        return r;
    }

    public static <T> R<T> error() {
        R<T> r = new R<>();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    public static <T> R<T> error(String message) {
        R<T> r = error();
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> error(Integer code, String msg) {
        R<T> r = new R<>();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static <T> R<T> setResult(ResultCodeEnum result) {
        R<T> r = new R<>();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /** --------- 链式编程支持 --------- **/

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
        return this;
    }

    public R<T> data(String key, Object value) {
        this.setData(null); // 保证 data 与 dataMap 不共存
        if (this.dataMap == null) {
            this.dataMap = new HashMap<>();
        }
        this.dataMap.put(key, value);
        return this;
    }

    public R<T> data(Map<String, Object> map) {
        this.setData(null); // 清空 data，切换为 dataMap 模式
        this.setDataMap(map);
        return this;
    }
}
