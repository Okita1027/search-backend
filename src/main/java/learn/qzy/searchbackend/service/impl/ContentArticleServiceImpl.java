package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.mapper.ContentArticleMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qzy
* @description 针对表【content_article】的数据库操作Service实现
* @createDate 2024-12-10 13:39:33
*/
@Service
public class ContentArticleServiceImpl extends ServiceImpl<ContentArticleMapper, ContentArticle>
    implements ContentArticleService{

    @Override
    public Result<ContentPicture> getArticleList(String title) {
        LambdaQueryWrapper<ContentArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContentArticle::getTitle, title);
        List<ContentArticle> articleList = this.list(wrapper);
        return ResultGenerator.genSuccessResult(articleList);
    }
}




