package learn.qzy.searchbackend.tmp;

import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.service.ContentUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月23日 21:39 星期三
 * @title
 */
@SpringBootTest
public class JsonTest {
    @Autowired
    private ContentUserService userService;

    @Test
    void test01() {
        ContentUser user = userService.getById(1);
        System.out.println("user.getFavorComment() = " + user.getFavorComment());
    }
}
