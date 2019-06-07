package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Fonction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FonctionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FonctionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Fonction.
 */
@Service
@Transactional
public class FonctionService {

    private final Logger log = LoggerFactory.getLogger(FonctionService.class);

    private final FonctionRepository fonctionRepository;

    private final FonctionSearchRepository fonctionSearchRepository;

    public FonctionService(FonctionRepository fonctionRepository, FonctionSearchRepository fonctionSearchRepository) {
        this.fonctionRepository = fonctionRepository;
        this.fonctionSearchRepository = fonctionSearchRepository;
    }

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save
     * @return the persisted entity
     */
    public Fonction save(Fonction fonction) {
        log.debug("Request to save Fonction : {}", fonction);
        Fonction result = fonctionRepository.save(fonction);
        fonctionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the fonctions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Fonction> findAll(Pageable pageable) {
        log.debug("Request to get all Fonctions");
        return fonctionRepository.findAll(pageable);
    }


    /**
     * Get one fonction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Fonction> findOne(Long id) {
        log.debug("Request to get Fonction : {}", id);
        return fonctionRepository.findById(id);
    }

    /**
     * Delete the fonction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fonction : {}", id);
        fonctionRepository.deleteById(id);
        fonctionSearchRepository.deleteById(id);
    }

    /**
     * Search for the fonction corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Fonction> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fonctions for query {}", query);
        return fonctionSearchRepository.search(queryStringQuery(query), pageable);    }
}
