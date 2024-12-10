package learn.qzy.searchbackend;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class SearchBackendApplicationTests {
    @Resource
    private UserService userService;
    @Test
    void test() {

    }

}
