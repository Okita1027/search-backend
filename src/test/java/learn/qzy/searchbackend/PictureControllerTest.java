package learn.qzy.searchbackend;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.service.ContentPictureService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author qzy
 * @create 2024/12/12 16:02 星期四
 * @title
 */
@SpringBootTest
public class PictureControllerTest {
    @Resource
    private ContentPictureService pictureService;

    @Test
    void testGetPicture() {
        pictureService.getPictureList("风景");
    }
}
