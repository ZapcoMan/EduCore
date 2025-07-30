package com.example.common.result;

import lombok.Getter;

/**
 * 定义了API响应的结果代码枚举
 * 这个枚举类用于统一定义系统中的响应码及其对应的信息，便于在返回结果时使用
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 表示操作成功的响应码
     */
    SUCCESS(true,20000,"成功"),
    /**
     * 表示未知错误的响应码
     */
    UNKNOWN_ERROR(false,20001,"未知错误"),
    /**
     * 表示参数错误的响应码
     */
    PARAM_ERROR(false,20002,"参数错误"),
    /**
     * 表示空指针错误的响应码
     */
    NULL_POINT(false,20003,"空指针错误"),
    /**
     * 表示HTTP客户端错误的响应码
     */
    HTTP_CLIENT_ERROR(false,20004,"客户端错误");

    // 成功状态标识
    private Boolean success;
    // 响应码
    private Integer code;
    // 响应消息
    private String message;

    /**
     * 构造函数，初始化响应码的属性
     *
     * @param success 表示操作是否成功
     * @param code    响应码
     * @param message 响应消息
     */
    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
