package com.example.common.exception;

import com.example.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义业务异常，包含异常代码与异常消息
 * 用于封装业务逻辑中出现的异常情况，提供统一的异常处理机制
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerException extends RuntimeException {

    private String code;
    private String msg;

    /**
     * 构造函数，使用指定的错误代码和消息创建异常
     * @param code 错误代码
     * @param msg 错误消息
     */
    public CustomerException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数，使用指定的消息创建异常，错误代码使用默认的未知错误代码
     * @param msg 错误消息
     */
    public CustomerException(String msg) {
        super(msg);
        this.code = String.valueOf(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        this.msg = msg;
    }

    /**
     * 构造函数，使用指定的错误代码和异常对象创建异常
     * @param code 错误代码
     * @param e 异常对象
     */
    public CustomerException(String code, Exception e) {
        super(e);
        this.code = code;
        this.msg = e.getMessage();
        log.warn(e.getMessage());
    }
}
