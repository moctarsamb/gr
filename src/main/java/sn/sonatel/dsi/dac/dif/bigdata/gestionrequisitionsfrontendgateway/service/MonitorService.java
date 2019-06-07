package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.MonitorRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.MonitorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Monitor.
 */
@Service
@Transactional
public class MonitorService {

    private final Logger log = LoggerFactory.getLogger(MonitorService.class);

    private final MonitorRepository monitorRepository;

    private final MonitorSearchRepository monitorSearchRepository;

    public MonitorService(MonitorRepository monitorRepository, MonitorSearchRepository monitorSearchRepository) {
        this.monitorRepository = monitorRepository;
        this.monitorSearchRepository = monitorSearchRepository;
    }

    /**
     * Save a monitor.
     *
     * @param monitor the entity to save
     * @return the persisted entity
     */
    public Monitor save(Monitor monitor) {
        log.debug("Request to save Monitor : {}", monitor);
        Monitor result = monitorRepository.save(monitor);
        monitorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the monitors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Monitor> findAll(Pageable pageable) {
        log.debug("Request to get all Monitors");
        return monitorRepository.findAll(pageable);
    }


    /**
     * Get one monitor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Monitor> findOne(Long id) {
        log.debug("Request to get Monitor : {}", id);
        return monitorRepository.findById(id);
    }

    /**
     * Delete the monitor by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Monitor : {}", id);
        monitorRepository.deleteById(id);
        monitorSearchRepository.deleteById(id);
    }

    /**
     * Search for the monitor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Monitor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Monitors for query {}", query);
        return monitorSearchRepository.search(queryStringQuery(query), pageable);    }
}
