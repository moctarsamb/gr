package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.RequisitionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.RequisitionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Requisition.
 */
@Service
@Transactional
public class RequisitionService {

    private final Logger log = LoggerFactory.getLogger(RequisitionService.class);

    private final RequisitionRepository requisitionRepository;

    private final RequisitionSearchRepository requisitionSearchRepository;

    public RequisitionService(RequisitionRepository requisitionRepository, RequisitionSearchRepository requisitionSearchRepository) {
        this.requisitionRepository = requisitionRepository;
        this.requisitionSearchRepository = requisitionSearchRepository;
    }

    /**
     * Save a requisition.
     *
     * @param requisition the entity to save
     * @return the persisted entity
     */
    public Requisition save(Requisition requisition) {
        log.debug("Request to save Requisition : {}", requisition);
        Requisition result = requisitionRepository.save(requisition);
        requisitionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the requisitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Requisition> findAll(Pageable pageable) {
        log.debug("Request to get all Requisitions");
        return requisitionRepository.findAll(pageable);
    }


    /**
     * Get one requisition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Requisition> findOne(Long id) {
        log.debug("Request to get Requisition : {}", id);
        return requisitionRepository.findById(id);
    }

    /**
     * Delete the requisition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Requisition : {}", id);
        requisitionRepository.deleteById(id);
        requisitionSearchRepository.deleteById(id);
    }

    /**
     * Search for the requisition corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Requisition> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Requisitions for query {}", query);
        return requisitionSearchRepository.search(queryStringQuery(query), pageable);    }
}
