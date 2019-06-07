package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ColonneRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ColonneSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Colonne.
 */
@Service
@Transactional
public class ColonneService {

    private final Logger log = LoggerFactory.getLogger(ColonneService.class);

    private final ColonneRepository colonneRepository;

    private final ColonneSearchRepository colonneSearchRepository;

    public ColonneService(ColonneRepository colonneRepository, ColonneSearchRepository colonneSearchRepository) {
        this.colonneRepository = colonneRepository;
        this.colonneSearchRepository = colonneSearchRepository;
    }

    /**
     * Save a colonne.
     *
     * @param colonne the entity to save
     * @return the persisted entity
     */
    public Colonne save(Colonne colonne) {
        log.debug("Request to save Colonne : {}", colonne);
        Colonne result = colonneRepository.save(colonne);
        colonneSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the colonnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Colonne> findAll(Pageable pageable) {
        log.debug("Request to get all Colonnes");
        return colonneRepository.findAll(pageable);
    }

    /**
     * Get all the Colonne with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Colonne> findAllWithEagerRelationships(Pageable pageable) {
        return colonneRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one colonne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Colonne> findOne(Long id) {
        log.debug("Request to get Colonne : {}", id);
        return colonneRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the colonne by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Colonne : {}", id);
        colonneRepository.deleteById(id);
        colonneSearchRepository.deleteById(id);
    }

    /**
     * Search for the colonne corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Colonne> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Colonnes for query {}", query);
        return colonneSearchRepository.search(queryStringQuery(query), pageable);    }
}
