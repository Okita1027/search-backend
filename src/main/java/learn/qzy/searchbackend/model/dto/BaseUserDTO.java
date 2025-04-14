package learn.qzy.searchbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author qzy
 * @email qinzhiyun1027@163.com
 * @create 2025年4月14日 14:19 星期一
 * @title （普通/管理员）用户实体基类
 */
@Getter
@Setter
public class BaseUserDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
