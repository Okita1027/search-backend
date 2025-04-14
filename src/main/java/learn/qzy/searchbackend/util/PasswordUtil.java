package learn.qzy.searchbackend.util;

import cn.hutool.crypto.digest.BCrypt;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月14日 9:42 星期一
 * @title 密码工具类
 */
public class PasswordUtil {
    /**
     * 加密密码
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encrypt(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * 校验密码是否正确
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
