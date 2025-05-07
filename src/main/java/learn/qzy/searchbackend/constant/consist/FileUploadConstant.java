package learn.qzy.searchbackend.constant.consist;

import java.util.List;
import java.util.Map;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月19日 16:15 星期六
 * @title 文件上传常量类
 */
public interface FileUploadConstant {

    String PICTURE_UPLOAD_URL = "D:\\DevData\\Web\\Project\\search-frontend\\public\\picture";
    String VIDEO_UPLOAD_URL = "D:\\DevData\\Web\\Project\\search-frontend\\public\\video";
    String AUDIO_UPLOAD_URL = "D:\\DevData\\Web\\Project\\search-frontend\\public\\audio";

    Map<String, List<String>> UPLOAD_TYPE_MAP = Map.of(
            "picture", List.of("jpg", "png", "jpeg", "gif"),
            "video", List.of("mp4", "avi", "wmv", "mov"),
            "audio", List.of("mp3", "wav", "aac", "flac")
    );

}
