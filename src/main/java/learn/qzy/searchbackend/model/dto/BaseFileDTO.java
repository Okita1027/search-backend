package learn.qzy.searchbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhiyun Qin
 * @email qinzhiyun1027@163.com
 * @create 2025/05/09  16:51  星期五
 * @title （图片、视频、音频）文件基类
 */
@Setter
@Getter
public class BaseFileDTO {
    private String fileName;
    private String filePath;
}
