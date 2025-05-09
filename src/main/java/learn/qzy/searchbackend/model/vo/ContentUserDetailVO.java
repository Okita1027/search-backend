package learn.qzy.searchbackend.model.vo;

import learn.qzy.searchbackend.model.dto.BaseFileDTO;
import learn.qzy.searchbackend.model.entity.ContentUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author Zhiyun Qin
 * @email qinzhiyun1027@163.com
 * @create 2025/05/09  16:47  星期五
 * @title 用户中心页面VO类
 */
@Getter
@Setter
public class ContentUserDetailVO extends ContentUser {
    /**
     * 文件列表，key为文件类型，value为文件列表
     */
    private Map<String, List<BaseFileDTO>> fileListMap;
}
