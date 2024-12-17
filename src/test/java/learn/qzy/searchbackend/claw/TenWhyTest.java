package learn.qzy.searchbackend.claw;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentArticle;
import learn.qzy.searchbackend.service.ContentArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TenWhyTest {
    @Resource
    private ContentArticleService articleService;

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
                System.out.println("title = " + title);
                String content = json.getStr("answer");
                System.out.println("content = " + content);

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
}
