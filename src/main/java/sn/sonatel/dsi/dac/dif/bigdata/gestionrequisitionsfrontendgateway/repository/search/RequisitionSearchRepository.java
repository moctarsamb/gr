package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Requisition entity.
 */
public interface RequisitionSearchRepository extends ElasticsearchRepository<Requisition, Long> {
}
