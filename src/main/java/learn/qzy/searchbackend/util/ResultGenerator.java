package learn.qzy.searchbackend.util;


/**
 * @author qzy
 * @create 2024/12/12 13:12 星期四
 * @title 结果生成工具类
 */
public class ResultGenerator {
    private static final int DEFAULT_SUCCESS_CODE = 200;
    private static final int DEFAULT_FAIL_CODE = 500;
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "ERROR";

    public static Result genSuccessResult() {
        Result result = new Result<>();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static Result genSuccessResult(String message) {
        Result result = new Result<>();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMessage(message);
        return result;
    }

    public static Result genSuccessResult(Object data) {
        Result result = new Result<>();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result genFailResult() {
        Result result = new Result<>();
        result.setCode(DEFAULT_FAIL_CODE);
        result.setMessage(DEFAULT_FAIL_MESSAGE);
        return result;
    }

    public static Result genFailResult(String message) {
        Result result = new Result<>();
        result.setCode(DEFAULT_FAIL_CODE);
        result.setMessage(message);
        return result;
    }

    public static Result genFailResult(int code, String message) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result genErrorResult(int code, String message) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
