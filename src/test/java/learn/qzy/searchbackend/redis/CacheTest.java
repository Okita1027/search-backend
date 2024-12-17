package learn.qzy.searchbackend.redis;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class CacheTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    void importCacheTest() {

    }
}
