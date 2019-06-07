package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ClauseRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ClauseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Clause.
 */
@Service
@Transactional
public class ClauseService {

    private final Logger log = LoggerFactory.getLogger(ClauseService.class);

    private final ClauseRepository clauseRepository;

    private final ClauseSearchRepository clauseSearchRepository;

    public ClauseService(ClauseRepository clauseRepository, ClauseSearchRepository clauseSearchRepository) {
        this.clauseRepository = clauseRepository;
        this.clauseSearchRepository = clauseSearchRepository;
    }

    /**
     * Save a clause.
     *
     * @param clause the entity to save
     * @return the persisted entity
     */
    public Clause save(Clause clause) {
        log.debug("Request to save Clause : {}", clause);
        Clause result = clauseRepository.save(clause);
        clauseSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the clauses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Clause> findAll(Pageable pageable) {
        log.debug("Request to get all Clauses");
        return clauseRepository.findAll(pageable);
    }

    /**
     * Get all the Clause with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Clause> findAllWithEagerRelationships(Pageable pageable) {
        return clauseRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one clause by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Clause> findOne(Long id) {
        log.debug("Request to get Clause : {}", id);
        return clauseRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the clause by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Clause : {}", id);
        clauseRepository.deleteById(id);
        clauseSearchRepository.deleteById(id);
    }

    /**
     * Search for the clause corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Clause> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clauses for query {}", query);
        return clauseSearchRepository.search(queryStringQuery(query), pageable);    }
}
