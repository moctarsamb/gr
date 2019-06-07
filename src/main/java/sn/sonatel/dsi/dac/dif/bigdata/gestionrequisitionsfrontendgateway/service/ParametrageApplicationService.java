package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ParametrageApplication;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ParametrageApplicationRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ParametrageApplicationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParametrageApplication.
 */
@Service
@Transactional
public class ParametrageApplicationService {

    private final Logger log = LoggerFactory.getLogger(ParametrageApplicationService.class);

    private final ParametrageApplicationRepository parametrageApplicationRepository;

    private final ParametrageApplicationSearchRepository parametrageApplicationSearchRepository;

    public ParametrageApplicationService(ParametrageApplicationRepository parametrageApplicationRepository, ParametrageApplicationSearchRepository parametrageApplicationSearchRepository) {
        this.parametrageApplicationRepository = parametrageApplicationRepository;
        this.parametrageApplicationSearchRepository = parametrageApplicationSearchRepository;
    }

    /**
     * Save a parametrageApplication.
     *
     * @param parametrageApplication the entity to save
     * @return the persisted entity
     */
    public ParametrageApplication save(ParametrageApplication parametrageApplication) {
        log.debug("Request to save ParametrageApplication : {}", parametrageApplication);
        ParametrageApplication result = parametrageApplicationRepository.save(parametrageApplication);
        parametrageApplicationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the parametrageApplications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ParametrageApplication> findAll(Pageable pageable) {
        log.debug("Request to get all ParametrageApplications");
        return parametrageApplicationRepository.findAll(pageable);
    }


    /**
     * Get one parametrageApplication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ParametrageApplication> findOne(Long id) {
        log.debug("Request to get ParametrageApplication : {}", id);
        return parametrageApplicationRepository.findById(id);
    }

    /**
     * Delete the parametrageApplication by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ParametrageApplication : {}", id);
        parametrageApplicationRepository.deleteById(id);
        parametrageApplicationSearchRepository.deleteById(id);
    }

    /**
     * Search for the parametrageApplication corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ParametrageApplication> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParametrageApplications for query {}", query);
        return parametrageApplicationSearchRepository.search(queryStringQuery(query), pageable);    }
}
