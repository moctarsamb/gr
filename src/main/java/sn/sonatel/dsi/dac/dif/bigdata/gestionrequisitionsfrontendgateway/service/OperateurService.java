package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperateurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Operateur.
 */
@Service
@Transactional
public class OperateurService {

    private final Logger log = LoggerFactory.getLogger(OperateurService.class);

    private final OperateurRepository operateurRepository;

    private final OperateurSearchRepository operateurSearchRepository;

    public OperateurService(OperateurRepository operateurRepository, OperateurSearchRepository operateurSearchRepository) {
        this.operateurRepository = operateurRepository;
        this.operateurSearchRepository = operateurSearchRepository;
    }

    /**
     * Save a operateur.
     *
     * @param operateur the entity to save
     * @return the persisted entity
     */
    public Operateur save(Operateur operateur) {
        log.debug("Request to save Operateur : {}", operateur);
        Operateur result = operateurRepository.save(operateur);
        operateurSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the operateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operateur> findAll(Pageable pageable) {
        log.debug("Request to get all Operateurs");
        return operateurRepository.findAll(pageable);
    }


    /**
     * Get one operateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Operateur> findOne(Long id) {
        log.debug("Request to get Operateur : {}", id);
        return operateurRepository.findById(id);
    }

    /**
     * Delete the operateur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operateur : {}", id);
        operateurRepository.deleteById(id);
        operateurSearchRepository.deleteById(id);
    }

    /**
     * Search for the operateur corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operateur> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operateurs for query {}", query);
        return operateurSearchRepository.search(queryStringQuery(query), pageable);    }
}
