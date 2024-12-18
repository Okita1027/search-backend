package learn.qzy.searchbackend.service;

import learn.qzy.searchbackend.model.entity.ContentArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.model.vo.ContentArticleVO;
import learn.qzy.searchbackend.util.Result;

/**
* @author qzy
* @description 针对表【content_article】的数据库操作Service
* @createDate 2024-12-10 13:39:33
*/
public interface ContentArticleService extends IService<ContentArticle> {

    Result<ContentArticleVO> getArticleList(String title);

    Result<String> getSuggestion(String text);
}
