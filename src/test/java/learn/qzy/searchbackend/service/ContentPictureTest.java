package learn.qzy.searchbackend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.vo.ContentPictureVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ContentPictureTest {
    @Resource
    private ContentPictureService pictureService;
    @Test
    void test01() {
        LambdaQueryWrapper<ContentPicture> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentPicture::getTitle, "风景");
        List<ContentPicture> pictureList = pictureService.list(wrapper);
        List<ContentPictureVO> pictureVOList = BeanUtil.copyToList(pictureList, ContentPictureVO.class);
        System.out.println("pictureVOList = " + pictureVOList);
    }
}
