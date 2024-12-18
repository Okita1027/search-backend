package learn.qzy.searchbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import learn.qzy.searchbackend.constant.enums.ErrorCodeEnum;
import learn.qzy.searchbackend.exception.ThrowUtils;
import learn.qzy.searchbackend.util.ESClient;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.vo.ContentArticleVO;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.mapper.ContentArticleMapper;
import learn.qzy.searchbackend.util.Result;
import learn.qzy.searchbackend.util.ResultGenerator;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qzy
 * @description 针对表【content_article】的数据库操作Service实现
 * @createDate 2024-12-10 13:39:33
 */
@Service
public class ContentArticleServiceImpl extends ServiceImpl<ContentArticleMapper, ContentArticle>
        implements ContentArticleService {
    private static final RestHighLevelClient client = ESClient.createClient();

    /**
     * 纠正检索词
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
            }
        });

        return ResultGenerator.genSuccessResult(articleList);
    }


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

}
