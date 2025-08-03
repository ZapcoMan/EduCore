package com.example.common.result;

import lombok.Getter;

/**
 * 全局通用状态码枚举，建议用于统一异常处理、响应封装等场景
 * 定义了系统中各种操作的返回状态码和对应的提示信息
 */
@Getter
public enum ResultCodeEnum  {

    /** ------------ 成功类 ------------ **/
    SUCCESS(true, 20000, "成功"),

    /** ------------ 通用错误类 ------------ **/
    UNKNOWN_ERROR(false, 20001, "系统未知异常"),
    PARAM_ERROR(false, 20002, "请求参数错误"),
    NULL_POINTER(false, 20003, "空指针异常"),
    HTTP_CLIENT_ERROR(false, 20004, "客户端请求错误"),

    /** ------------ 认证授权相关 ------------ **/
    UNAUTHORIZED(false, 40100, "未登录或 Token 过期"),
    FORBIDDEN(false, 40300, "无权限访问"),

    /** ------------ 用户相关 ------------ **/
    USER_NOT_FOUND(false, 60001, "用户不存在"),
    USER_DISABLED(false, 60002, "用户已被禁用");

    /** 是否成功 */
    private final Boolean success;

    /** 状态码 */
    private final Integer code;

    /** 响应信息（可作为默认提示） */
    private final String message;

    /**
     * 构造函数，用于初始化枚举值
     *
     * @param success 操作是否成功
     * @param code 状态码
     * @param message 响应信息
     */
    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
