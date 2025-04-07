package learn.qzy.searchbackend.exception;

import learn.qzy.searchbackend.constant.enums.ErrorCodeEnum;

/**
 * @author qzy
 * @create 2024/12/10 13:58 星期二
 * @title 抛出异常工具类
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param runtimeException
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     */
    public static void throwIf(boolean condition, ErrorCodeEnum errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition
     * @param errorCode
     * @param message
     */
    public static void throwIf(boolean condition, ErrorCodeEnum errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}