package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Resultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ResultatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Resultat.
 */
@Service
@Transactional
public class ResultatService {

    private final Logger log = LoggerFactory.getLogger(ResultatService.class);

    private final ResultatRepository resultatRepository;

    private final ResultatSearchRepository resultatSearchRepository;

    public ResultatService(ResultatRepository resultatRepository, ResultatSearchRepository resultatSearchRepository) {
        this.resultatRepository = resultatRepository;
        this.resultatSearchRepository = resultatSearchRepository;
    }

    /**
     * Save a resultat.
     *
     * @param resultat the entity to save
     * @return the persisted entity
     */
    public Resultat save(Resultat resultat) {
        log.debug("Request to save Resultat : {}", resultat);
        Resultat result = resultatRepository.save(resultat);
        resultatSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the resultats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Resultat> findAll(Pageable pageable) {
        log.debug("Request to get all Resultats");
        return resultatRepository.findAll(pageable);
    }


    /**
     * Get one resultat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Resultat> findOne(Long id) {
        log.debug("Request to get Resultat : {}", id);
        return resultatRepository.findById(id);
    }

    /**
     * Delete the resultat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Resultat : {}", id);
        resultatRepository.deleteById(id);
        resultatSearchRepository.deleteById(id);
    }

    /**
     * Search for the resultat corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Resultat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Resultats for query {}", query);
        return resultatSearchRepository.search(queryStringQuery(query), pageable);    }
}
