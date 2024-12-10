package learn.qzy.searchbackend;

import learn.qzy.searchbackend.model.entity.ContentPicture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class HitokotoTest {
    @Test
    void testHitokoto() throws IOException {
        String url = "https://v1.hitokoto.cn/";
        Document doc = Jsoup.connect(url).get();
        System.out.println("doc.title() = " + doc.title());
        /*        // 分析页面结构，寻找图片元素
        Elements imageElements = doc.select("img[src]"); // 选择所有具有 src 属性的 <img> 元素

        // 输出图片链接
        for (Element imageElement : imageElements) {
            String imageUrl = imageElement.attr("src");
            System.out.println(imageUrl);
            ContentPicture picture = new ContentPicture();
            picture.setTitle("风景");
            picture.setPictureUrl(imageUrl);
        }*/
    }
}
