package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OperateurLogique entity.
 */
public interface OperateurLogiqueSearchRepository extends ElasticsearchRepository<OperateurLogique, Long> {
}
