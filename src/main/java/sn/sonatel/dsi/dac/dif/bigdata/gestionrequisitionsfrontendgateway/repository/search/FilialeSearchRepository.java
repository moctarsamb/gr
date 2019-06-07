package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Filiale entity.
 */
public interface FilialeSearchRepository extends ElasticsearchRepository<Filiale, Long> {
}
