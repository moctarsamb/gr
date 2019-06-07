package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Valeur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ValeurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ValeurSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Valeur.
 */
@Service
@Transactional
public class ValeurService {

    private final Logger log = LoggerFactory.getLogger(ValeurService.class);

    private final ValeurRepository valeurRepository;

    private final ValeurSearchRepository valeurSearchRepository;

    public ValeurService(ValeurRepository valeurRepository, ValeurSearchRepository valeurSearchRepository) {
        this.valeurRepository = valeurRepository;
        this.valeurSearchRepository = valeurSearchRepository;
    }

    /**
     * Save a valeur.
     *
     * @param valeur the entity to save
     * @return the persisted entity
     */
    public Valeur save(Valeur valeur) {
        log.debug("Request to save Valeur : {}", valeur);
        Valeur result = valeurRepository.save(valeur);
        valeurSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the valeurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Valeur> findAll(Pageable pageable) {
        log.debug("Request to get all Valeurs");
        return valeurRepository.findAll(pageable);
    }


    /**
     * Get one valeur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Valeur> findOne(Long id) {
        log.debug("Request to get Valeur : {}", id);
        return valeurRepository.findById(id);
    }

    /**
     * Delete the valeur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Valeur : {}", id);
        valeurRepository.deleteById(id);
        valeurSearchRepository.deleteById(id);
    }

    /**
     * Search for the valeur corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Valeur> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Valeurs for query {}", query);
        return valeurSearchRepository.search(queryStringQuery(query), pageable);    }
}
