package learn.qzy.searchbackend.es;

import learn.qzy.searchbackend.util.ESClient;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


/**
 * 分页查询
 */
@SpringBootTest
public class ScrollQueryTest {
    static RestHighLevelClient client = ESClient.createClient();

    /**
     * scroll分批查询
     */
    @Test
    void test() throws IOException {
        SearchRequest searchRequest = new SearchRequest("content_article");

        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1));
        searchRequest.scroll(scroll);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "为什么"));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        String scrollId = searchResponse.getScrollId();

        // 处理第一次查询的结果
        searchResponse.getHits().forEach(hit -> {
            System.out.println(hit.getSourceAsString());
        });

        // 使用 scrollID 获取接下来的结果
        while (searchResponse.getHits().getHits().length > 0) {
            SearchScrollRequest searchScrollRequest = new SearchScrollRequest();
            searchScrollRequest.scrollId(scrollId);
            searchScrollRequest.scroll(scroll);

            searchResponse = client.searchScroll(searchScrollRequest, RequestOptions.DEFAULT);

            // 获取新的滚动ID
            scrollId = searchResponse.getScrollId();

            // 处理当前批次的结果
            searchResponse.getHits().forEach(hit -> {
                System.out.println(hit.getSourceAsString());
            });
        }
    }

    /**
     * 分页查询
     */
    @Test
    void testPage() throws IOException {
        int pageNumber = 2;
        int pageSize = 10;

        // 创建搜索请求
        SearchRequest searchRequest = new SearchRequest("content_article");

        // 创建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 设置分页查询
        int from = (pageNumber - 1) * pageSize;  // 计算从哪条数据开始
        searchSourceBuilder.from(from);           // 设置从哪条数据开始
        searchSourceBuilder.size(pageSize);       // 设置每页的记录数

        // 设置查询条件（例如，匹配所有文档）
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "为什么"));

        // 将查询条件添加到请求中
        searchRequest.source(searchSourceBuilder);

        // 执行查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理查询结果
        searchResponse.getHits().forEach(hit -> {
            System.out.println(hit.getSourceAsString());
        });
    }

}
