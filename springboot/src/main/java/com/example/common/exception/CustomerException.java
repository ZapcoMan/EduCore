package com.example.common.exception;

import com.example.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


/**
 * 自定义业务异常，包含异常代码与异常消息
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerException extends RuntimeException {

    private String code;
    private String msg;

    public CustomerException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomerException(String msg) {
        super(msg);
        this.code = String.valueOf(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        this.msg = msg;
    }

    public CustomerException(String code, Exception e) {
        super(e);
        this.code = code;
        this.msg = e.getMessage();
        log.warn(e.getMessage());
    }
}
