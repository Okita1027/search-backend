package learn.qzy.searchbackend.esdao;

import learn.qzy.searchbackend.model.es.ESContentUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<ESContentUser, Long> {

}
