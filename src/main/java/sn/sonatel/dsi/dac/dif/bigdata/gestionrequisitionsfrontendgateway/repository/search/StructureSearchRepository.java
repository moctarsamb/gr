package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Structure entity.
 */
public interface StructureSearchRepository extends ElasticsearchRepository<Structure, Long> {
}
