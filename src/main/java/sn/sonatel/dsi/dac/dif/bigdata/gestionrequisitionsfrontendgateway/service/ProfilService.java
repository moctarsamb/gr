package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ProfilRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProfilSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Profil.
 */
@Service
@Transactional
public class ProfilService {

    private final Logger log = LoggerFactory.getLogger(ProfilService.class);

    private final ProfilRepository profilRepository;

    private final ProfilSearchRepository profilSearchRepository;

    public ProfilService(ProfilRepository profilRepository, ProfilSearchRepository profilSearchRepository) {
        this.profilRepository = profilRepository;
        this.profilSearchRepository = profilSearchRepository;
    }

    /**
     * Save a profil.
     *
     * @param profil the entity to save
     * @return the persisted entity
     */
    public Profil save(Profil profil) {
        log.debug("Request to save Profil : {}", profil);
        Profil result = profilRepository.save(profil);
        profilSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the profils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Profil> findAll(Pageable pageable) {
        log.debug("Request to get all Profils");
        return profilRepository.findAll(pageable);
    }

    /**
     * Get all the Profil with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Profil> findAllWithEagerRelationships(Pageable pageable) {
        return profilRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one profil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Profil> findOne(Long id) {
        log.debug("Request to get Profil : {}", id);
        return profilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the profil by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
        profilSearchRepository.deleteById(id);
    }

    /**
     * Search for the profil corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Profil> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Profils for query {}", query);
        return profilSearchRepository.search(queryStringQuery(query), pageable);    }
}
