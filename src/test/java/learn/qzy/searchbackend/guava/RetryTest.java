package learn.qzy.searchbackend.guava;

import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@SpringBootTest
public class RetryTest {
    @Test
    void test01() throws IOException {
        AtomicReference<Elements> imageElements = new AtomicReference<>();
        // 定义可能出错的任务
        Callable<Boolean> fetchData = () -> {
            String title = "风景";
            String url = "https://unsplash.com/s/photos/" + title;
            Document doc = Jsoup.connect(url).get();
            // 选择所有具有 src 属性的 <img> 元素
            imageElements.set(doc.select("img[src]"));
            return imageElements.get() != null;
        };

        // 定义重试策略
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException() // 如果抛出异常则重试
                .retryIfResult(result -> !result) // 如果结果为false则重试
                .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS)) // 每次重试等待2秒
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 最多重试3次
                .build();

        // 执行任务并自动重试
        try {
            boolean result = retryer.call(fetchData);
            System.out.println("任务成功: " + result);
        } catch (RetryException | ExecutionException e) {
            System.err.println("任务失败: " + e.getMessage());
        }

    }

}