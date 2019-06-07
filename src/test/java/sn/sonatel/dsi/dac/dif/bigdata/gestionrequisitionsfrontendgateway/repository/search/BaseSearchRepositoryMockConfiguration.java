package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BaseSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BaseSearchRepositoryMockConfiguration {

    @MockBean
    private BaseSearchRepository mockBaseSearchRepository;

}
