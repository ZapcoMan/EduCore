package com.example.common.exception;

import com.example.common.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获器
 */
// 控制器增强类，用于全局异常处理
@ControllerAdvice("com.example.controller")
public class GlobalExceptionHandler {
    // 日志对象，用于记录异常信息
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理系统异常
     *
     * @param e 异常对象
     * @return 返回错误信息封装对象
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody // 将result对象转换成 json的格式
    public R error(Exception e) {
        // 记录系统异常日志
        log.error("系统异常", e);
        // 返回系统异常错误信息
        return R.error("系统异常");
    }

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常对象
     * @return 返回错误信息封装对象
     */
    @ExceptionHandler(CustomerException.class)
    @ResponseBody // 将result对象转换成 json的格式
    public R customerError(CustomerException e) {
        // 记录自定义异常日志
        log.error("出现了未知的错误", e);
        // 返回自定义异常错误信息
        return R.error(Integer.valueOf(e.getCode()), e.getMsg());
    }

}
