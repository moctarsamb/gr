package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FiltreRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FiltreSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Filtre.
 */
@Service
@Transactional
public class FiltreService {

    private final Logger log = LoggerFactory.getLogger(FiltreService.class);

    private final FiltreRepository filtreRepository;

    private final FiltreSearchRepository filtreSearchRepository;

    public FiltreService(FiltreRepository filtreRepository, FiltreSearchRepository filtreSearchRepository) {
        this.filtreRepository = filtreRepository;
        this.filtreSearchRepository = filtreSearchRepository;
    }

    /**
     * Save a filtre.
     *
     * @param filtre the entity to save
     * @return the persisted entity
     */
    public Filtre save(Filtre filtre) {
        log.debug("Request to save Filtre : {}", filtre);
        Filtre result = filtreRepository.save(filtre);
        filtreSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the filtres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Filtre> findAll(Pageable pageable) {
        log.debug("Request to get all Filtres");
        return filtreRepository.findAll(pageable);
    }

    /**
     * Get all the Filtre with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Filtre> findAllWithEagerRelationships(Pageable pageable) {
        return filtreRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one filtre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Filtre> findOne(Long id) {
        log.debug("Request to get Filtre : {}", id);
        return filtreRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the filtre by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Filtre : {}", id);
        filtreRepository.deleteById(id);
        filtreSearchRepository.deleteById(id);
    }

    /**
     * Search for the filtre corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Filtre> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Filtres for query {}", query);
        return filtreSearchRepository.search(queryStringQuery(query), pageable);    }
}
