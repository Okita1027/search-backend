package learn.qzy.searchbackend;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import learn.qzy.searchbackend.model.entity.ContentPicture;
import learn.qzy.searchbackend.service.ContentPictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author qzy
 * @create 2024/12/12 14:07 星期四
 * @title
 */
@SpringBootTest
public class JsoupTest {
//    @Resource
//    private PostService postService;

    @Resource
    private ContentPictureService pictureService;


    @Test
    void testUnsplash() throws IOException {
        String url = "https://unsplash.com/s/photos/风景";
        Document doc = Jsoup.connect(url).get();
        // 分析页面结构，寻找图片元素
        Elements imageElements = doc.select("img[src]"); // 选择所有具有 src 属性的 <img> 元素

        // 输出图片链接
        for (Element imageElement : imageElements) {
            String imageUrl = imageElement.attr("src");
            System.out.println(imageUrl);
            ContentPicture picture = new ContentPicture();
            picture.setTitle("风景");
            picture.setPictureUrl(imageUrl);
//            pictureService.deleteExistsPicture("风景");
            pictureService.save(picture);
        }
    }

    @Test
    void testDeletePicture() {
        pictureService.deleteExistsPicture("风景");
    }


    /*@Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=风景&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<ContentPicture> pictures = new ArrayList<>();
        for (Element element : elements) {
            // 取图片地址（murl）
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
//            System.out.println(murl);
            // 取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
//            System.out.println(title);
            ContentPicture picture = new ContentPicture();
            picture.setTitle(title);
            picture.setPictureUrl(murl);
            pictures.add(picture);
        }
        System.out.println(pictures);
    }*/

/*    @Test
    void testFetchPassage() {
        // 1. 获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
//        System.out.println(result);
        // 2. json 转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
//        System.out.println(postList);
        // 3. 数据入库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }*/
}
