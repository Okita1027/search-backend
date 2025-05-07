package learn.qzy.searchbackend.exception;

import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static learn.qzy.searchbackend.constant.enums.ErrorCodeEnum.SYSTEM_ERROR;

/**
 * @author qzy
 * @create 2024/12/10 13:50 星期二
 * @title 全局异常处理器
 */
//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException e) {
        return ResultGenerator.genFailResult(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        return ResultGenerator.genErrorResult(SYSTEM_ERROR.getCode(), e.getMessage());
    }
}