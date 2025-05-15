package learn.qzy.searchbackend.mapper;

import learn.qzy.searchbackend.model.entity.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import learn.qzy.searchbackend.model.vo.ArticleCommentVO;

import java.util.List;

/**
* @author Administrator
* @description 针对表【article_comment(文章评论表)】的数据库操作Mapper
* @createDate 2025-04-06 13:38:41
* @Entity learn.qzy.searchbackend.model/entity.ArticleComment
*/
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    Integer selectMaxSerialNumberByArticleId(Long articleId);

    List<ArticleCommentVO> selectListAll();
}




