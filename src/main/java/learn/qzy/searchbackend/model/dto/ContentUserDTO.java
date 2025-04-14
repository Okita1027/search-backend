package learn.qzy.searchbackend.model.dto;

import learn.qzy.searchbackend.model.entity.ContentUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月14日 13:55 星期一
 * @title 用户信息DTO
 */
@Getter
@Setter
public class ContentUserDTO extends ContentUser {
    /**
     * 原始密码
     */
    private String rawPassword;
}
