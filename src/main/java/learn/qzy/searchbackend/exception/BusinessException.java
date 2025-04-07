package learn.qzy.searchbackend.exception;

import learn.qzy.searchbackend.constant.enums.ErrorCodeEnum;
import lombok.Getter;

/**
 * @author qzy
 * @create 2024/12/10 13:50 星期二
 * @title 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum, String message) {
        super(message);
        this.code = errorCodeEnum.getCode();
    }

}

