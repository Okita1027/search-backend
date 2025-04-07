package learn.qzy.searchbackend.es;

import jakarta.annotation.Resource;
import learn.qzy.searchbackend.util.ESClient;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.entity.ContentUser;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.service.ContentUserService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RestHighLevelClientTest {
    static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"title\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"content\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    RestHighLevelClient client = ESClient.createClient();
    @Test
    void testCreateIndex() throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("content_article");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteIndex() throws IOException {
        // 1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest("content_article");
        // 2.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsIndex() throws IOException {
        // 1.创建Request对象
        GetIndexRequest request = new GetIndexRequest("content_article");
        // 2.发送请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        // 3.输出
        System.out.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }


    @Test
    void createDocumentTest() throws IOException {
        // 创建索引请求
        IndexRequest request = new IndexRequest("content_article");
        request.id("1");
        request.source("title", "Java基础教程", "content", "Java 是由 Sun Microsystems 公司于 1995 年 5 月推出的高级程序设计语言。\n" +
                "\n" +
                "Java 可运行于多个平台，如 Windows, Mac OS 及其他多种 UNIX 版本的系统。\n" +
                "\n" +
                "本教程通过简单的实例将让大家更好的了解 Java 编程语言。\n" +
                "\n" +
                "移动操作系统 Android 大部分的代码采用 Java 编程语言编程。");
        // 执行索引操作
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void getDocumentTest() throws IOException {
        // 创建一个获取文档的请求
        GetRequest request = new GetRequest("content_article", "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 获取文档内容
        if (response.isExists()) {
            String title = response.getSourceAsMap().get("title").toString();
            String content = response.getSourceAsMap().get("content").toString();
            System.out.println("title = " + title);
            System.out.println("content = " + content);
        } else {
            System.out.println("该文档不存在！");
        }
    }

    @Test
    void updateDocument() throws IOException {
        // 创建更新请求
        UpdateRequest request = new UpdateRequest("content_article", "1")
                .doc("title", "Java基础教程", "content", "Spring全家桶从入门到精通到入土……");
        // 执行更新操作
        client.update(request, RequestOptions.DEFAULT);
    }

    @Test
    void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("content_article", "1");
        client.delete(request, RequestOptions.DEFAULT);
    }


    @Test
    void searchDocument() throws IOException {
        // 创建搜索请求
        SearchRequest request = new SearchRequest("content_article");
        // 构建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title", "Java教程"));
//        builder.query(QueryBuilders.matchQuery("content", "java"));
        request.source(builder);

        // 执行搜索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println("hit = " + hit);
            String title = (String) hit.getSourceAsMap().get("title");
            System.out.println("title = " + title);
        }
    }

    /**
     * 高亮搜索
     */
    @Test
    void searchDocumentHighLight() throws IOException {
        // 创建搜索请求
        SearchRequest request = new SearchRequest("content_article");
        // 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "Java"));
        // 模糊搜索：即允许输入的词略有出入
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("title", "jaava").fuzziness(Fuzziness.AUTO));

        // 高亮构建器
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").preTags("<em>").postTags("</em>");
        searchSourceBuilder.highlighter(highlightBuilder);

        request.source(searchSourceBuilder);

        // 执行搜索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

/*        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println("hit = " + hit);
        }*/

        // 获取（高亮）结果
        response.getHits().forEach(hit -> {
            String content = hit.getSourceAsMap().get("content").toString();
            if (hit.getHighlightFields().containsKey("title")) {
                String highlightedDescription = hit.getHighlightFields().get("title").fragments()[0].string();
                System.out.println("Highlighted title: " + highlightedDescription);
            }
            System.out.println("content = " + content);
        });
    }

    String getOptions(String indexName, String suggestName, String field, String text) throws IOException {
        // 创建搜索请求
        SearchRequest request = new SearchRequest(indexName);

        // 创建建议构建器
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(
                suggestName,
                new TermSuggestionBuilder(field)
                        .text(text)  // 输入的文本
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
                if (entry.getOptions().size() > 1){
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

    @Test
    void testGetOptions() throws IOException {
        String res = getOptions("content_article", "title_suggest", "title", "javc教程");
//        String res = getOptions("content_article", "title_suggest", "title", "englsh teqh");

        System.out.println("result = " + res);
    }

    @Test
    public void getSuggestions() throws IOException {
        // 创建搜索请求
        SearchRequest request = new SearchRequest("content_article");

        // 创建建议构建器
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(
                "title_suggest",
                new TermSuggestionBuilder("title")
                        .text("javaa教程")  // 输入的文本
        );

        // 将建议构建器加入请求
        request.source().suggest(suggestBuilder);

        // 执行搜索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 处理建议结果
        TermSuggestion titleSuggestion = response.getSuggest().getSuggestion("title_suggest");
        List<TermSuggestion.Entry> entries = titleSuggestion.getEntries();

        // 打印每个建议的结果
        for (TermSuggestion.Entry entry : entries) {
            System.out.println("Suggested text: " + entry.getText());
            entry.getOptions().forEach(option -> System.out.println("Option text: " + option.getText()));
        }
    }

    /**
     * 导入MySQL（文章）数据到ES
     */
    @Resource
    private ContentArticleService articleService;
    @Test
    void bulkCreateArticleDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<ContentArticle> articleList = articleService.list();
        for (ContentArticle article : articleList) {
            Long id = article.getId();
            String title = article.getTitle();
            String content = article.getContent();
            IndexRequest indexRequest = new IndexRequest("content_article");
            indexRequest.id(String.valueOf(id));
            indexRequest.source("title", title, "content", content);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    /**
     * 导入MySQL（用户）数据到ES
     */
    @Resource
    private ContentUserService userService;
    @Test
    void bulkCreateUserDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        List<ContentUser> userList = userService.list();
        for (ContentUser user : userList) {
            Long id = user.getId();
            String title = user.getNickname();
            String profile = user.getProfile();
            String avatarUrl = user.getAvatarUrl();
            IndexRequest indexRequest = new IndexRequest("content_user");
            indexRequest.id(String.valueOf(id));
            indexRequest.source("title", title, "profile", profile, "avatar_url", avatarUrl);
            bulkRequest.add(indexRequest);
        }
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    @AfterEach
    void closeClient() throws Exception {
        ESClient.closeClient(client);
    }
}
