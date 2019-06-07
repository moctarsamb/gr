package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperandeRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperandeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Operande.
 */
@Service
@Transactional
public class OperandeService {

    private final Logger log = LoggerFactory.getLogger(OperandeService.class);

    private final OperandeRepository operandeRepository;

    private final OperandeSearchRepository operandeSearchRepository;

    public OperandeService(OperandeRepository operandeRepository, OperandeSearchRepository operandeSearchRepository) {
        this.operandeRepository = operandeRepository;
        this.operandeSearchRepository = operandeSearchRepository;
    }

    /**
     * Save a operande.
     *
     * @param operande the entity to save
     * @return the persisted entity
     */
    public Operande save(Operande operande) {
        log.debug("Request to save Operande : {}", operande);
        Operande result = operandeRepository.save(operande);
        operandeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the operandes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operande> findAll(Pageable pageable) {
        log.debug("Request to get all Operandes");
        return operandeRepository.findAll(pageable);
    }

    /**
     * Get all the Operande with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Operande> findAllWithEagerRelationships(Pageable pageable) {
        return operandeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one operande by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Operande> findOne(Long id) {
        log.debug("Request to get Operande : {}", id);
        return operandeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the operande by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operande : {}", id);
        operandeRepository.deleteById(id);
        operandeSearchRepository.deleteById(id);
    }

    /**
     * Search for the operande corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operande> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operandes for query {}", query);
        return operandeSearchRepository.search(queryStringQuery(query), pageable);    }
}
