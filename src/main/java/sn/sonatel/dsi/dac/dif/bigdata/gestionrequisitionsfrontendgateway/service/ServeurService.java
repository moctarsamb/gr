package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Serveur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ServeurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ServeurSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Serveur.
 */
@Service
@Transactional
public class ServeurService {

    private final Logger log = LoggerFactory.getLogger(ServeurService.class);

    private final ServeurRepository serveurRepository;

    private final ServeurSearchRepository serveurSearchRepository;

    public ServeurService(ServeurRepository serveurRepository, ServeurSearchRepository serveurSearchRepository) {
        this.serveurRepository = serveurRepository;
        this.serveurSearchRepository = serveurSearchRepository;
    }

    /**
     * Save a serveur.
     *
     * @param serveur the entity to save
     * @return the persisted entity
     */
    public Serveur save(Serveur serveur) {
        log.debug("Request to save Serveur : {}", serveur);
        Serveur result = serveurRepository.save(serveur);
        serveurSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the serveurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Serveur> findAll(Pageable pageable) {
        log.debug("Request to get all Serveurs");
        return serveurRepository.findAll(pageable);
    }


    /**
     * Get one serveur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Serveur> findOne(Long id) {
        log.debug("Request to get Serveur : {}", id);
        return serveurRepository.findById(id);
    }

    /**
     * Delete the serveur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Serveur : {}", id);
        serveurRepository.deleteById(id);
        serveurSearchRepository.deleteById(id);
    }

    /**
     * Search for the serveur corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Serveur> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Serveurs for query {}", query);
        return serveurSearchRepository.search(queryStringQuery(query), pageable);    }
}
