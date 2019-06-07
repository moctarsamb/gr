package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.CritereRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.CritereSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Critere.
 */
@Service
@Transactional
public class CritereService {

    private final Logger log = LoggerFactory.getLogger(CritereService.class);

    private final CritereRepository critereRepository;

    private final CritereSearchRepository critereSearchRepository;

    public CritereService(CritereRepository critereRepository, CritereSearchRepository critereSearchRepository) {
        this.critereRepository = critereRepository;
        this.critereSearchRepository = critereSearchRepository;
    }

    /**
     * Save a critere.
     *
     * @param critere the entity to save
     * @return the persisted entity
     */
    public Critere save(Critere critere) {
        log.debug("Request to save Critere : {}", critere);
        Critere result = critereRepository.save(critere);
        critereSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the criteres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Critere> findAll(Pageable pageable) {
        log.debug("Request to get all Criteres");
        return critereRepository.findAll(pageable);
    }


    /**
     * Get one critere by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Critere> findOne(Long id) {
        log.debug("Request to get Critere : {}", id);
        return critereRepository.findById(id);
    }

    /**
     * Delete the critere by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Critere : {}", id);
        critereRepository.deleteById(id);
        critereSearchRepository.deleteById(id);
    }

    /**
     * Search for the critere corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Critere> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Criteres for query {}", query);
        return critereSearchRepository.search(queryStringQuery(query), pageable);    }
}
