package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Typologie;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypologieRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypologieSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Typologie.
 */
@Service
@Transactional
public class TypologieService {

    private final Logger log = LoggerFactory.getLogger(TypologieService.class);

    private final TypologieRepository typologieRepository;

    private final TypologieSearchRepository typologieSearchRepository;

    public TypologieService(TypologieRepository typologieRepository, TypologieSearchRepository typologieSearchRepository) {
        this.typologieRepository = typologieRepository;
        this.typologieSearchRepository = typologieSearchRepository;
    }

    /**
     * Save a typologie.
     *
     * @param typologie the entity to save
     * @return the persisted entity
     */
    public Typologie save(Typologie typologie) {
        log.debug("Request to save Typologie : {}", typologie);
        Typologie result = typologieRepository.save(typologie);
        typologieSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the typologies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Typologie> findAll(Pageable pageable) {
        log.debug("Request to get all Typologies");
        return typologieRepository.findAll(pageable);
    }


    /**
     * Get one typologie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Typologie> findOne(Long id) {
        log.debug("Request to get Typologie : {}", id);
        return typologieRepository.findById(id);
    }

    /**
     * Delete the typologie by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Typologie : {}", id);
        typologieRepository.deleteById(id);
        typologieSearchRepository.deleteById(id);
    }

    /**
     * Search for the typologie corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Typologie> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Typologies for query {}", query);
        return typologieSearchRepository.search(queryStringQuery(query), pageable);    }
}
