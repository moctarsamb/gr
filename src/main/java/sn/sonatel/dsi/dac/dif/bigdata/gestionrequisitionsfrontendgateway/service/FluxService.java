package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FluxRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FluxSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Flux.
 */
@Service
@Transactional
public class FluxService {

    private final Logger log = LoggerFactory.getLogger(FluxService.class);

    private final FluxRepository fluxRepository;

    private final FluxSearchRepository fluxSearchRepository;

    public FluxService(FluxRepository fluxRepository, FluxSearchRepository fluxSearchRepository) {
        this.fluxRepository = fluxRepository;
        this.fluxSearchRepository = fluxSearchRepository;
    }

    /**
     * Save a flux.
     *
     * @param flux the entity to save
     * @return the persisted entity
     */
    public Flux save(Flux flux) {
        log.debug("Request to save Flux : {}", flux);
        Flux result = fluxRepository.save(flux);
        fluxSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the fluxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Flux> findAll(Pageable pageable) {
        log.debug("Request to get all Fluxes");
        return fluxRepository.findAll(pageable);
    }


    /**
     * Get one flux by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Flux> findOne(Long id) {
        log.debug("Request to get Flux : {}", id);
        return fluxRepository.findById(id);
    }

    /**
     * Delete the flux by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Flux : {}", id);
        fluxRepository.deleteById(id);
        fluxSearchRepository.deleteById(id);
    }

    /**
     * Search for the flux corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Flux> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fluxes for query {}", query);
        return fluxSearchRepository.search(queryStringQuery(query), pageable);    }
}
