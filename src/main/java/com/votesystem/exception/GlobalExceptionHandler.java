package com.votesystem.exception;

import com.votesystem.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ricardo
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常类
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomersException.class)
    @ResponseBody
    public Result customerException(CustomersException exception) {
        return Result.error(exception.getCode(), exception.getMsg());
    }
}
