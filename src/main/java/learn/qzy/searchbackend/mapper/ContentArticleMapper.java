package learn.qzy.searchbackend.mapper;

import learn.qzy.searchbackend.model.entity.ContentArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author qzy
* @description 针对表【content_article】的数据库操作Mapper
* @createDate 2024-12-10 13:39:33
* @Entity generator.ContentArticle
*/
public interface ContentArticleMapper extends BaseMapper<ContentArticle> {

    List<ContentArticle> selectListAll();

}




