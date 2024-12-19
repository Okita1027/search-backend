package learn.qzy.searchbackend.guava;

import com.github.rholder.retry.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class RetryExample {

    public static void main(String[] args) {
        // 定义重试策略
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException() // 如果抛出异常则重试
                .retryIfResult(result -> !result) // 如果结果为false则重试
                .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS)) // 每次重试等待2秒
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 最多重试3次
                .build();

        // 定义需要重试的任务
        Callable<Boolean> task = RetryExample::fetchData;

        try {
            // 执行任务并自动重试
            boolean result = retryer.call(task);
            System.out.println("任务成功: " + result);
        } catch (RetryException | ExecutionException e) {
            System.err.println("任务失败: " + e.getMessage());
        }
    }

    // 模拟一个可能失败的任务
    public static boolean fetchData() throws Exception {
        System.out.println("尝试获取数据...");
        // 模拟网络故障
        if (Math.random() < 0.6) {
            throw new Exception("网络错误");
        }
        return true;
    }
}