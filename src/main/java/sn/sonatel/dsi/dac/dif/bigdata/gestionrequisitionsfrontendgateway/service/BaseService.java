package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.BaseRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.BaseSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Base.
 */
@Service
@Transactional
public class BaseService {

    private final Logger log = LoggerFactory.getLogger(BaseService.class);

    private final BaseRepository baseRepository;

    private final BaseSearchRepository baseSearchRepository;

    public BaseService(BaseRepository baseRepository, BaseSearchRepository baseSearchRepository) {
        this.baseRepository = baseRepository;
        this.baseSearchRepository = baseSearchRepository;
    }

    /**
     * Save a base.
     *
     * @param base the entity to save
     * @return the persisted entity
     */
    public Base save(Base base) {
        log.debug("Request to save Base : {}", base);
        Base result = baseRepository.save(base);
        baseSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Base> findAll(Pageable pageable) {
        log.debug("Request to get all Bases");
        return baseRepository.findAll(pageable);
    }


    /**
     * Get one base by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Base> findOne(Long id) {
        log.debug("Request to get Base : {}", id);
        return baseRepository.findById(id);
    }

    /**
     * Delete the base by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Base : {}", id);
        baseRepository.deleteById(id);
        baseSearchRepository.deleteById(id);
    }

    /**
     * Search for the base corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Base> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bases for query {}", query);
        return baseSearchRepository.search(queryStringQuery(query), pageable);    }
}
