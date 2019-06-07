package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.EnvironnementRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvironnementSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Environnement.
 */
@Service
@Transactional
public class EnvironnementService {

    private final Logger log = LoggerFactory.getLogger(EnvironnementService.class);

    private final EnvironnementRepository environnementRepository;

    private final EnvironnementSearchRepository environnementSearchRepository;

    public EnvironnementService(EnvironnementRepository environnementRepository, EnvironnementSearchRepository environnementSearchRepository) {
        this.environnementRepository = environnementRepository;
        this.environnementSearchRepository = environnementSearchRepository;
    }

    /**
     * Save a environnement.
     *
     * @param environnement the entity to save
     * @return the persisted entity
     */
    public Environnement save(Environnement environnement) {
        log.debug("Request to save Environnement : {}", environnement);
        Environnement result = environnementRepository.save(environnement);
        environnementSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the environnements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Environnement> findAll(Pageable pageable) {
        log.debug("Request to get all Environnements");
        return environnementRepository.findAll(pageable);
    }


    /**
     * Get one environnement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Environnement> findOne(Long id) {
        log.debug("Request to get Environnement : {}", id);
        return environnementRepository.findById(id);
    }

    /**
     * Delete the environnement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Environnement : {}", id);
        environnementRepository.deleteById(id);
        environnementSearchRepository.deleteById(id);
    }

    /**
     * Search for the environnement corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Environnement> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Environnements for query {}", query);
        return environnementSearchRepository.search(queryStringQuery(query), pageable);    }
}
