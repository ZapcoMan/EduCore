package com.example.common.exception;

import com.example.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 自定义客户异常类，继承自RuntimeException
 * 用于处理客户相关的异常情况，提供异常代码和消息的封装
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerException extends RuntimeException {
    /**
     * 异常代码，用于标识异常类型
     */
    private String code;
    /**
     * 异常消息，描述异常的具体信息
     */
    private String msg;

    /**
     * 构造函数，初始化异常代码和消息
     *
     * @param code 异常代码
     * @param msg  异常消息
     */
    public CustomerException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数，当异常代码未知时使用
     * 使用未知错误的默认异常代码
     *
     * @param msg 异常消息
     */
    public CustomerException(String msg) {
        this.code = String.valueOf(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        this.msg = msg;
    }
}
