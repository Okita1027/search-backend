package learn.qzy.searchbackend.model.vo;

import learn.qzy.searchbackend.model.entity.ContentUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhiyun Qin
 * @email qinzhiyun1027@163.com
 * @create 2025/05/11  9:02  星期日
 * @title (后台管理系统)用户管理VO类
 */
@Setter
@Getter
public class AdminContentUserVO extends ContentUser {
    private boolean isLogin;
}
