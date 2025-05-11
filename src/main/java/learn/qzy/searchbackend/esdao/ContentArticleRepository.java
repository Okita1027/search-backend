package learn.qzy.searchbackend.esdao;

import learn.qzy.searchbackend.model.es.ESContentArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author qzy
 * @time 2024年12月19日 20:40 星期四
 * @title
 */
public interface ContentArticleRepository extends ElasticsearchRepository<ESContentArticle, String> {
    /**
     * 根据title查找文章是否存在
     */
    boolean existsByTitle(String title);

    ESContentArticle findByTitle(String title);
}
