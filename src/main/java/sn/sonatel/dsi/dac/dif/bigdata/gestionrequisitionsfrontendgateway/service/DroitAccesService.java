package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.DroitAcces;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.DroitAccesRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DroitAccesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DroitAcces.
 */
@Service
@Transactional
public class DroitAccesService {

    private final Logger log = LoggerFactory.getLogger(DroitAccesService.class);

    private final DroitAccesRepository droitAccesRepository;

    private final DroitAccesSearchRepository droitAccesSearchRepository;

    public DroitAccesService(DroitAccesRepository droitAccesRepository, DroitAccesSearchRepository droitAccesSearchRepository) {
        this.droitAccesRepository = droitAccesRepository;
        this.droitAccesSearchRepository = droitAccesSearchRepository;
    }

    /**
     * Save a droitAcces.
     *
     * @param droitAcces the entity to save
     * @return the persisted entity
     */
    public DroitAcces save(DroitAcces droitAcces) {
        log.debug("Request to save DroitAcces : {}", droitAcces);
        DroitAcces result = droitAccesRepository.save(droitAcces);
        droitAccesSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the droitAcces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DroitAcces> findAll(Pageable pageable) {
        log.debug("Request to get all DroitAcces");
        return droitAccesRepository.findAll(pageable);
    }


    /**
     * Get one droitAcces by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DroitAcces> findOne(Long id) {
        log.debug("Request to get DroitAcces : {}", id);
        return droitAccesRepository.findById(id);
    }

    /**
     * Delete the droitAcces by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DroitAcces : {}", id);
        droitAccesRepository.deleteById(id);
        droitAccesSearchRepository.deleteById(id);
    }

    /**
     * Search for the droitAcces corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DroitAcces> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DroitAcces for query {}", query);
        return droitAccesSearchRepository.search(queryStringQuery(query), pageable);    }
}
