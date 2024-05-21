package com.rabbiter.dm.config;

import com.rabbiter.dm.exception.HttpException;
import com.rabbiter.dm.utils.HttpCode;
import com.rabbiter.dm.vo.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rabbiter
 * @date 2022-01-07
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * http 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(HttpException.class)
    public Result httpExceptionHandler(HttpException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 验证失败异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.fail(HttpCode.FAILED, "请正确填写参数").add(e.getMessage());
    }

    /**
     * 默认异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        return Result.fail(HttpCode.FAILED, "服务器未知错误").add(e.getMessage());
    }
}
