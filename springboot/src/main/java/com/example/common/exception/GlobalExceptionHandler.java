package com.example.common.exception;

import com.example.common.result.R;
import com.example.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 处理系统中未被捕获的异常，提供统一的异常响应格式
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.controller")
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     * @param e CustomerException业务异常对象
     * @return 统一响应结果R
     */
    @ExceptionHandler(CustomerException.class)
    public R<?> handleCustomerException(CustomerException e) {
        log.warn("业务异常 - code: {}, message: {}", e.getCode(), e.getMsg());
        return R.error(Integer.valueOf(e.getCode()), e.getMsg());
    }

    /**
     * 处理请求参数相关的异常，包括绑定异常、缺少请求参数异常、消息不可读异常
     * @param e 异常对象
     * @return 统一响应结果R
     */
    @ExceptionHandler({BindException.class, MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    public R<?> handleBadRequestException(Exception e) {
        log.warn("请求参数异常: {}", e.getMessage());
        return R.error(ResultCodeEnum.PARAM_ERROR.getCode(), "请求参数错误或格式不正确");
    }

    /**
     * 处理其他未被捕获的异常
     * @param e Exception异常对象
     * @return 统一响应结果R
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        log.error("系统异常:", e);
        return R.error(ResultCodeEnum.UNKNOWN_ERROR.getCode(), ResultCodeEnum.UNKNOWN_ERROR.getMessage());
    }
}
