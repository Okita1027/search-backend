package learn.qzy.searchbackend.redis;

import cn.hutool.core.bean.BeanUtil;
import learn.qzy.searchbackend.model.vo.ContentPictureVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    void testSet() {
//        redisTemplate.opsForValue().set("name", "qzy");
//        redisTemplate.delete("name");
//        redisTemplate.opsForList().leftPush("风景", "https://www.baidu.com");
//        redisTemplate.opsForList().leftPush("风景", "https://pan.baidu.com");
//        redisTemplate.opsForList().leftPush("风景", "https://yiyan.baidu.com");
    }

    @Test
    void testGet() {
/*        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < redisTemplate.opsForList().size("风景"); i++) {
            list.add(redisTemplate.opsForList().leftPop("风景"));
        }
        System.out.println("list = " + list);*/

        List<String> list = redisTemplate.opsForList().range("风景", 0, -1);
        ArrayList<ContentPictureVO> result = new ArrayList<>();
        for (String str : list) {
            result.add(new ContentPictureVO(str));
        }
        System.out.println("result = " + result);
    }
}
