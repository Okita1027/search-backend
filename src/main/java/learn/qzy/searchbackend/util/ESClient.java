package learn.qzy.searchbackend.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author qzy
 * @create 2024/12/15 19:08 星期日
 * @title ESClient客户端配置
 */
public class ESClient {
    public static RestHighLevelClient createClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );
    }

    public static void closeClient(RestHighLevelClient client) throws Exception {
        if (client != null)
            client.close();
    }
}
