package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.DimensionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DimensionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Dimension.
 */
@Service
@Transactional
public class DimensionService {

    private final Logger log = LoggerFactory.getLogger(DimensionService.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionSearchRepository dimensionSearchRepository;

    public DimensionService(DimensionRepository dimensionRepository, DimensionSearchRepository dimensionSearchRepository) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionSearchRepository = dimensionSearchRepository;
    }

    /**
     * Save a dimension.
     *
     * @param dimension the entity to save
     * @return the persisted entity
     */
    public Dimension save(Dimension dimension) {
        log.debug("Request to save Dimension : {}", dimension);
        Dimension result = dimensionRepository.save(dimension);
        dimensionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dimensions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Dimension> findAll(Pageable pageable) {
        log.debug("Request to get all Dimensions");
        return dimensionRepository.findAll(pageable);
    }

    /**
     * Get all the Dimension with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Dimension> findAllWithEagerRelationships(Pageable pageable) {
        return dimensionRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one dimension by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Dimension> findOne(Long id) {
        log.debug("Request to get Dimension : {}", id);
        return dimensionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the dimension by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Dimension : {}", id);
        dimensionRepository.deleteById(id);
        dimensionSearchRepository.deleteById(id);
    }

    /**
     * Search for the dimension corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Dimension> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Dimensions for query {}", query);
        return dimensionSearchRepository.search(queryStringQuery(query), pageable);    }
}
