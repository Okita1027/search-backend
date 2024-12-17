package learn.qzy.searchbackend;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.service.ContentUserService;
import learn.qzy.searchbackend.util.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContentUserTest {
    @Resource
    private ContentUserService userService;
    @Test
    void test() {
        Result<ContentPicture> user = userService.getUserList("ok");
        System.out.println("user = " + user.getData());
    }
}
