package learn.qzy.searchbackend.model.vo;

import learn.qzy.searchbackend.model.entity.ArticleComment;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zhiyun Qin
 * @email qinzhiyun1027@163.com
 * @create 2025/05/15  8:16  星期四
 * @title 文章评论VO
 */
@Getter
@Setter
public class ArticleCommentVO extends ArticleComment {
    /**
     * 文章标题
     */
    private String articleTitle;

}
