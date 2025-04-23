package learn.qzy.searchbackend.constant.enums;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月14日 13:08 星期一
 * @title 业务失败错误码
 */
public enum FailureCodeEnum {
    FILE_UPLOAD_FAILURE(40700, "文件上传异常");

    private final int code;
    private final String message;

    FailureCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
