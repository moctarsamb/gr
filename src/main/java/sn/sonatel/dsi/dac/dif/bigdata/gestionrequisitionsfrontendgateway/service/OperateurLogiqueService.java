package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperateurLogiqueRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurLogiqueSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OperateurLogique.
 */
@Service
@Transactional
public class OperateurLogiqueService {

    private final Logger log = LoggerFactory.getLogger(OperateurLogiqueService.class);

    private final OperateurLogiqueRepository operateurLogiqueRepository;

    private final OperateurLogiqueSearchRepository operateurLogiqueSearchRepository;

    public OperateurLogiqueService(OperateurLogiqueRepository operateurLogiqueRepository, OperateurLogiqueSearchRepository operateurLogiqueSearchRepository) {
        this.operateurLogiqueRepository = operateurLogiqueRepository;
        this.operateurLogiqueSearchRepository = operateurLogiqueSearchRepository;
    }

    /**
     * Save a operateurLogique.
     *
     * @param operateurLogique the entity to save
     * @return the persisted entity
     */
    public OperateurLogique save(OperateurLogique operateurLogique) {
        log.debug("Request to save OperateurLogique : {}", operateurLogique);
        OperateurLogique result = operateurLogiqueRepository.save(operateurLogique);
        operateurLogiqueSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the operateurLogiques.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OperateurLogique> findAll(Pageable pageable) {
        log.debug("Request to get all OperateurLogiques");
        return operateurLogiqueRepository.findAll(pageable);
    }


    /**
     * Get one operateurLogique by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OperateurLogique> findOne(Long id) {
        log.debug("Request to get OperateurLogique : {}", id);
        return operateurLogiqueRepository.findById(id);
    }

    /**
     * Delete the operateurLogique by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OperateurLogique : {}", id);
        operateurLogiqueRepository.deleteById(id);
        operateurLogiqueSearchRepository.deleteById(id);
    }

    /**
     * Search for the operateurLogique corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OperateurLogique> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OperateurLogiques for query {}", query);
        return operateurLogiqueSearchRepository.search(queryStringQuery(query), pageable);    }
}
