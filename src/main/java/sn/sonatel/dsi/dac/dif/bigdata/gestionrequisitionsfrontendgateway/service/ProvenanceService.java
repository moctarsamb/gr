package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Provenance;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ProvenanceRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProvenanceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Provenance.
 */
@Service
@Transactional
public class ProvenanceService {

    private final Logger log = LoggerFactory.getLogger(ProvenanceService.class);

    private final ProvenanceRepository provenanceRepository;

    private final ProvenanceSearchRepository provenanceSearchRepository;

    public ProvenanceService(ProvenanceRepository provenanceRepository, ProvenanceSearchRepository provenanceSearchRepository) {
        this.provenanceRepository = provenanceRepository;
        this.provenanceSearchRepository = provenanceSearchRepository;
    }

    /**
     * Save a provenance.
     *
     * @param provenance the entity to save
     * @return the persisted entity
     */
    public Provenance save(Provenance provenance) {
        log.debug("Request to save Provenance : {}", provenance);
        Provenance result = provenanceRepository.save(provenance);
        provenanceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the provenances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Provenance> findAll(Pageable pageable) {
        log.debug("Request to get all Provenances");
        return provenanceRepository.findAll(pageable);
    }


    /**
     * Get one provenance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Provenance> findOne(Long id) {
        log.debug("Request to get Provenance : {}", id);
        return provenanceRepository.findById(id);
    }

    /**
     * Delete the provenance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Provenance : {}", id);
        provenanceRepository.deleteById(id);
        provenanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the provenance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Provenance> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Provenances for query {}", query);
        return provenanceSearchRepository.search(queryStringQuery(query), pageable);    }
}
