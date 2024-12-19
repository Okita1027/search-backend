package learn.qzy.searchbackend.schedule;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import learn.qzy.searchbackend.esdao.ContentArticleRepository;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.es.ESContentArticle;
import learn.qzy.searchbackend.service.ContentArticleService;
import learn.qzy.searchbackend.util.ESClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @time 2024/12/9 19:56 星期四
 * @author qzy
 * @title 定时抓取文章进MySQL和ElasticSearch
 */
@Component
public class ArticleCrawlerTask {

    @Autowired
    private ContentArticleService articleService;
    @Autowired
    private ContentArticleRepository articleRepository;

    // 定时任务：每隔 5 分钟抓取一次数据
    @Scheduled(fixedRate = 300000)
    public void fetchArticle() {
        try {
            String url = "https://api.52vmy.cn/api/wl/s/jzw";

            // 使用 Hutool 发起 GET 请求并获取响应
            String response = HttpRequest.get(url)
                    .timeout(5000)
                    .execute()
                    .body();

            // 解析 JSON 响应
            JSONObject jsonObject = JSONUtil.parseObj(response);
            String result = jsonObject.getStr("data");
            JSONObject json = JSONUtil.parseObj(result);
            String title = json.getStr("question");
            String content = json.getStr("answer");

            // 创建文章对象并保存进MySQL和ElasticSearch
            ContentArticle article = new ContentArticle();
            article.setTitle(title);
            article.setContent(content);
            articleService.save(article);

            // 判断是否该标题的文章，如果不存在则保存进ElasticSearch
            if (!articleRepository.existsByTitle(title)) {
                ESContentArticle esContentArticle = new ESContentArticle();
                esContentArticle.setTitle(title);
                esContentArticle.setContent(content);
                articleRepository.save(esContentArticle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fetch Data Error!");
        }
    }
}
