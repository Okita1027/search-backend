package learn.qzy.searchbackend.schedule;

import learn.qzy.searchbackend.mapper.ContentPictureMapper;
import learn.qzy.searchbackend.service.ContentPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qzy
 * @time 2024年12月31日 22:41 星期二
 * @title 每隔一天清除MySQL中的图片数据
 */
@Component
public class PictureClearTask {

    @Autowired
    private ContentPictureService pictureService;
    @Autowired
    private ContentPictureMapper pictureMapper;

    @Transactional
    @Scheduled(initialDelay = 24 * 60 * 60 * 1000, fixedRate = 24 * 60 * 60 * 1000)
    public void clearPicture() {
        // 清除所有图片数据
//        pictureService.remove(null);
        // 重置ID计数为1
//        pictureMapper.executeResetID("ALTER TABLE content_picture AUTO_INCREMENT = 1");

        // 直接截断表
        pictureMapper.executeResetID("TRUNCATE TABLE content_picture");
    }
}
