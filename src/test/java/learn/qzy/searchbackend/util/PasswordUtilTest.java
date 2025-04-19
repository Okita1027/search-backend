package learn.qzy.searchbackend.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月14日 9:44 星期一
 * @title 密码工具类测试
 */
@SpringBootTest
public class PasswordUtilTest {

    @Test
    void encryptTest() {
        String password = "root";
        String encryptPassword = PasswordUtil.encrypt(password);
        System.out.println(encryptPassword);
    }

    public static void main(String[] args) {
        String password = "jh";
        String encryptPassword = PasswordUtil.encrypt(password);
        System.out.println(encryptPassword);
        boolean matches = PasswordUtil.matches("123456", encryptPassword);
        System.out.println("matches = " + matches);
    }
}
