package learn.qzy.searchbackend.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Zhiyun Qin
 * @email 2368932388@qq.com
 * @time 2025年4月06日 15:03 星期日
 * @title 文章的评论、点赞 整合
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentLikeDTO {

    /**
     * 评论ID，方便点赞
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父类：评论用户名、昵称；
     * 当前：评论用户名、昵称
     */
    private String parentUsername;
    private String parentNickname;
    private String currentUsername;
    private String currentNickname;

    /**
     * 评论时间
     */
    private LocalDateTime createTime;


    /**
     * 点赞数量
     */
    private Long likeCount;

}
