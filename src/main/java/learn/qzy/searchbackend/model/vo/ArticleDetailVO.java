package learn.qzy.searchbackend.model.vo;

import learn.qzy.searchbackend.model.dto.CommentLikeDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author qzy
 * @time 2025年4月06日 14:52 星期日
 * @title 文章详情页面
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {

    private String title;

    private String content;

    private String createBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Map<Integer, List<CommentLikeDTO>> commentLikeDtoMap;

}
