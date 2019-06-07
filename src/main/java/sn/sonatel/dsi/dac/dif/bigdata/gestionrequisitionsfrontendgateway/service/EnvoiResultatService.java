package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.EnvoiResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvoiResultatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EnvoiResultat.
 */
@Service
@Transactional
public class EnvoiResultatService {

    private final Logger log = LoggerFactory.getLogger(EnvoiResultatService.class);

    private final EnvoiResultatRepository envoiResultatRepository;

    private final EnvoiResultatSearchRepository envoiResultatSearchRepository;

    public EnvoiResultatService(EnvoiResultatRepository envoiResultatRepository, EnvoiResultatSearchRepository envoiResultatSearchRepository) {
        this.envoiResultatRepository = envoiResultatRepository;
        this.envoiResultatSearchRepository = envoiResultatSearchRepository;
    }

    /**
     * Save a envoiResultat.
     *
     * @param envoiResultat the entity to save
     * @return the persisted entity
     */
    public EnvoiResultat save(EnvoiResultat envoiResultat) {
        log.debug("Request to save EnvoiResultat : {}", envoiResultat);
        EnvoiResultat result = envoiResultatRepository.save(envoiResultat);
        envoiResultatSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the envoiResultats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EnvoiResultat> findAll(Pageable pageable) {
        log.debug("Request to get all EnvoiResultats");
        return envoiResultatRepository.findAll(pageable);
    }


    /**
     * Get one envoiResultat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EnvoiResultat> findOne(Long id) {
        log.debug("Request to get EnvoiResultat : {}", id);
        return envoiResultatRepository.findById(id);
    }

    /**
     * Delete the envoiResultat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EnvoiResultat : {}", id);
        envoiResultatRepository.deleteById(id);
        envoiResultatSearchRepository.deleteById(id);
    }

    /**
     * Search for the envoiResultat corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EnvoiResultat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EnvoiResultats for query {}", query);
        return envoiResultatSearchRepository.search(queryStringQuery(query), pageable);    }
}
