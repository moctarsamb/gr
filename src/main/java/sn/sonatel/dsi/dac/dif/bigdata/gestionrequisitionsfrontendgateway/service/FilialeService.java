package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FilialeRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FilialeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Filiale.
 */
@Service
@Transactional
public class FilialeService {

    private final Logger log = LoggerFactory.getLogger(FilialeService.class);

    private final FilialeRepository filialeRepository;

    private final FilialeSearchRepository filialeSearchRepository;

    public FilialeService(FilialeRepository filialeRepository, FilialeSearchRepository filialeSearchRepository) {
        this.filialeRepository = filialeRepository;
        this.filialeSearchRepository = filialeSearchRepository;
    }

    /**
     * Save a filiale.
     *
     * @param filiale the entity to save
     * @return the persisted entity
     */
    public Filiale save(Filiale filiale) {
        log.debug("Request to save Filiale : {}", filiale);
        Filiale result = filialeRepository.save(filiale);
        filialeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the filiales.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Filiale> findAll(Pageable pageable) {
        log.debug("Request to get all Filiales");
        return filialeRepository.findAll(pageable);
    }

    /**
     * Get all the Filiale with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Filiale> findAllWithEagerRelationships(Pageable pageable) {
        return filialeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one filiale by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Filiale> findOne(Long id) {
        log.debug("Request to get Filiale : {}", id);
        return filialeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the filiale by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Filiale : {}", id);
        filialeRepository.deleteById(id);
        filialeSearchRepository.deleteById(id);
    }

    /**
     * Search for the filiale corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Filiale> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Filiales for query {}", query);
        return filialeSearchRepository.search(queryStringQuery(query), pageable);    }
}
