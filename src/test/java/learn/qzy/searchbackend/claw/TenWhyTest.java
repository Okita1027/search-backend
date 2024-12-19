package learn.qzy.searchbackend.claw;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.esdao.ContentArticleRepository;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.model.es.ESContentArticle;
import learn.qzy.searchbackend.service.ContentArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TenWhyTest {
    @Resource
    private ContentArticleService articleService;
    @Autowired
    private ContentArticleRepository articleRepository;

    @Test
    void test10Why() {
        try {
            for (int i = 0; i < 50; i++) {
                String url = "https://whyta.cn/api/tx/tenwhy?key=f922bcb04085";

                // 使用 Hutool 发起 GET 请求并获取响应
                String response = HttpRequest.get(url)
                        .timeout(5000)  // 设置超时时间
                        .execute()
                        .body();  // 获取响应体

                JSONObject jsonObject = JSONUtil.parseObj(response);
                String result = jsonObject.getStr("result");
                JSONObject jsonObject1 = JSONUtil.parseObj(result);
                String list = jsonObject1.getStr("list");
                JSONObject json = (JSONObject) JSONUtil.parseArray(list).get(0);
                String title = json.getStr("title");
//            System.out.println("title = " + title);
                String content = json.getStr("content");
//            System.out.println("content = " + content);

                ContentArticle article = new ContentArticle();
                article.setTitle(title);
                article.setContent(content);
                articleService.save(article);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testQW() {
        try {
            for (int i = 0; i < 50; i++) {
                // 脑筋急转弯
                String url = "https://api.52vmy.cn/api/wl/s/jzw";

                // 使用 Hutool 发起 GET 请求并获取响应
                String response = HttpRequest.get(url)
                        .timeout(5000)  // 设置超时时间
                        .execute()
                        .body();  // 获取响应体

                JSONObject jsonObject = JSONUtil.parseObj(response);
                String result = jsonObject.getStr("data");
                JSONObject json = JSONUtil.parseObj(result);
                String title = json.getStr("question");
                String content = json.getStr("answer");

                ContentArticle article = new ContentArticle();
                article.setTitle(title);
                article.setContent(content);
                articleService.save(article);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void fetchArticle() {
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