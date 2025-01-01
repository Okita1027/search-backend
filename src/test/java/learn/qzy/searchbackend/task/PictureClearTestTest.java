package learn.qzy.searchbackend.task;

import learn.qzy.searchbackend.schedule.PictureClearTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author qzy
 * @time 2024年12月31日 23:00 星期二
 * @title
 */
@SpringBootTest
public class PictureClearTestTest {
    @Autowired
    private PictureClearTask pictureClearTask;
    @Test
    void test01() {
        pictureClearTask.clearPicture();
    }
}
