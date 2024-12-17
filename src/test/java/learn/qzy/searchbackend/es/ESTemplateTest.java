package learn.qzy.searchbackend.es;

import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.service.ContentUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import java.util.List;

@SpringBootTest
public class ESTemplateTest {
    /*@Autowired
    private UserRepository userRepository;
    @Test
    void test01() {
        ESContentUser qzy = new ESContentUser(2L, "qzy", "qzy", "https://qzy.qzy.com/avatar.png", LocalDateTime.now(), LocalDateTime.now(), 0);
        userRepository.save(qzy);
    }*/
    @Autowired
    private ElasticsearchTemplate esTemplate;
    @Autowired
    private ContentUserService userService;
    @Test
    void test02() {
        List<ContentUser> userList = userService.list();
        esTemplate.save(userList);

    }


}
