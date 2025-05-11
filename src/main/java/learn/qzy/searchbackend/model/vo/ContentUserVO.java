package learn.qzy.searchbackend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author qzy
 * @create 2024/12/12 15:28 星期四
 * @title 聚合搜索页面的用户信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentUserVO {

    private String username;
    private String nickname;
    private String profile;
    private String avatarUrl;

}
