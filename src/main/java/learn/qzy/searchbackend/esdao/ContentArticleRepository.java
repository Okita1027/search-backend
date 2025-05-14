package learn.qzy.searchbackend.esdao;

import learn.qzy.searchbackend.model.es.ESContentArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author qzy
 * @time 2024年12月19日 20:40 星期四
 * @title ContentArticleRepository
 */
public interface ContentArticleRepository extends ElasticsearchRepository<ESContentArticle, String> {

    boolean existsByTitle(String title);

    ESContentArticle findByTitle(String title);
}
