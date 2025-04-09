package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.mapper.ContentArticleMapper;
import learn.qzy.searchbackend.model.dto.CommentLikeDTO;
import learn.qzy.searchbackend.model.entity.ArticleComment;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.vo.ArticleDetailVO;
import learn.qzy.searchbackend.model.vo.ContentArticleVO;
import learn.qzy.searchbackend.service.ArticleCommentService;
import learn.qzy.searchbackend.service.CommentLikeService;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.util.ESClient;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qzy
 * @description 针对表【content_article】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class ContentArticleServiceImpl extends ServiceImpl<ContentArticleMapper, ContentArticle>
        implements ContentArticleService {

    private static final RestHighLevelClient client = ESClient.createClient();

    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private CommentLikeService commentLikeService;


    /**
     * 获取搜索建议
     *
     * @param text 搜索词
     * @return 搜索建议
     */
    @Override
    public Result<String> getSuggestion(String text) {
        LambdaQueryWrapper<ContentArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(ContentArticle::getTitle, text);
        wrapper.last("LIMIT 10");
        List<ContentArticle> list = this.list(wrapper);
        List<String> result = list.stream().map(ContentArticle::getTitle).toList();
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 纠正检索词 / 模糊搜索
     *
     * @param indexName   索引库名称
     * @param suggestName 建议名称
     * @param field       字段名称
     * @param text        搜索字符串
     */
    String getOptions(String indexName, String suggestName, String field, String text) throws IOException {
        // 创建搜索请求
        SearchRequest request = new SearchRequest(indexName);

        // 创建建议构建器
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(
                suggestName,
                new TermSuggestionBuilder(field)
                        .text(text)
        );

        // 将建议构建器加入请求
        request.source().suggest(suggestBuilder);

        // 执行搜索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 处理建议结果
        TermSuggestion titleSuggestion = response.getSuggest().getSuggestion(suggestName);
        List<TermSuggestion.Entry> entries = titleSuggestion.getEntries();

        ArrayList<String> list = new ArrayList<>();
        for (TermSuggestion.Entry entry : entries) {
            for (TermSuggestion.Entry.Option option : entry.getOptions()) {
                // 若存在匹配分数相同的字符串，则只取其中一个
                if (entry.getOptions().size() > 1) {
                    list.add(String.valueOf(option.getText()));
                    break;
                }
            }
            // 若字符串完全匹配，则加入最终搜索串
            if (entry.getOptions().isEmpty()) {
                list.add(String.valueOf(entry.getText()));
            }
        }

        StringBuilder result = new StringBuilder();
        for (String str : list) {
            result.append(str);
        }

        return String.valueOf(result);
    }

    /**
     * 获取文章列表
     *
     * @param title 标题
     * @return 文章列表
     */
    @Override
    public Result<ContentArticleVO> getArticleList(String title) {
        // 创建搜索请求
        SearchRequest request = new SearchRequest("content_article");
        // 构建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 最多查询出1000条数据
        builder.size(1000);
        String searchText = null;
        try {
            searchText = getOptions("content_article", "title_suggest", "title", title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        builder.query(QueryBuilders.matchQuery("title", searchText));

        // 创建高亮构建
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").preTags("<em>").postTags("</em>");
        builder.highlighter(highlightBuilder);

        request.source(builder);

        // 执行搜索
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // 获取高亮结果
        ArrayList<ContentArticleVO> articleList = new ArrayList<>();
        response.getHits().forEach(hit -> {
            String content = hit.getSourceAsMap().get("content").toString();
            if (hit.getHighlightFields().containsKey("title")) {
                String highLightTitle = hit.getHighlightFields().get("title").fragments()[0].string();
                articleList.add(new ContentArticleVO(highLightTitle, content));
                System.out.println("highLightTitle = " + highLightTitle);
            }
        });

        return ResultGenerator.genSuccessResult(articleList);
    }


    /**
     * 获取文章详情
     *
     * @param text 文章标题
     * @return 文章详情
     */
    @Override
    public Result<ArticleDetailVO> getArticleDetail(String text) {
        ArticleDetailVO result = new ArticleDetailVO();
        Map<Integer, List<CommentLikeDTO>> dtoMap = new HashMap<>();

        // 查询文章
        text = text.replace("<em>", "");
        text = text.replace("</em>", "");
        LambdaQueryWrapper<ContentArticle> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(ContentArticle::getTitle, text);
        ContentArticle article = this.getOne(wrapper1);

        // 填充Result数据
        result.setTitle(article.getTitle());
        result.setContent(article.getContent());
        result.setCreateTime(article.getCreateTime());
        result.setUpdateTime(article.getUpdateTime());
        result.setCreateBy(article.getCreateBy());

        // 查询评论
        int serialNumber = 1;
        LambdaQueryWrapper<ArticleComment> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(ArticleComment::getArticleId, article.getId());
        wrapper2.eq(ArticleComment::getSerialNumber, serialNumber);
        List<ArticleComment> commentList = articleCommentService.list(wrapper2);
        while (commentList != null && !commentList.isEmpty()) {
            for (ArticleComment comment : commentList) {
                // 查询评论点赞数量
                Long likeCount = commentLikeService.getLikeCountByCommentId(comment.getId());
                List<CommentLikeDTO> dtoList = dtoMap.getOrDefault(comment.getSerialNumber(), new ArrayList<>());
                dtoList.add(new CommentLikeDTO(comment.getId(), comment.getContent(), comment.getParentUsername(), comment.getParentNickname(),
                        comment.getCurrentUsername(), comment.getCurrentNickname(), comment.getCreateTime(), likeCount));
                dtoMap.put(comment.getSerialNumber(), dtoList);
            }
            // 查询下一楼层的评论
            serialNumber++;
            wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(ArticleComment::getArticleId, article.getId());
            wrapper2.eq(ArticleComment::getSerialNumber, serialNumber);
            commentList = articleCommentService.list(wrapper2);
        }
        result.setCommentLikeDtoMap(dtoMap);

        return ResultGenerator.genSuccessResult(result);
    }

}
