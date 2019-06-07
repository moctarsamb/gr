package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.MonitorService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.MonitorCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.MonitorQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Monitor.
 */
@RestController
@RequestMapping("/api")
public class MonitorResource {

    private final Logger log = LoggerFactory.getLogger(MonitorResource.class);

    private static final String ENTITY_NAME = "monitor";

    private final MonitorService monitorService;

    private final MonitorQueryService monitorQueryService;

    public MonitorResource(MonitorService monitorService, MonitorQueryService monitorQueryService) {
        this.monitorService = monitorService;
        this.monitorQueryService = monitorQueryService;
    }

    /**
     * POST  /monitors : Create a new monitor.
     *
     * @param monitor the monitor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new monitor, or with status 400 (Bad Request) if the monitor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/monitors")
    public ResponseEntity<Monitor> createMonitor(@Valid @RequestBody Monitor monitor) throws URISyntaxException {
        log.debug("REST request to save Monitor : {}", monitor);
        if (monitor.getId() != null) {
            throw new BadRequestAlertException("A new monitor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monitor result = monitorService.save(monitor);
        return ResponseEntity.created(new URI("/api/monitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /monitors : Updates an existing monitor.
     *
     * @param monitor the monitor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated monitor,
     * or with status 400 (Bad Request) if the monitor is not valid,
     * or with status 500 (Internal Server Error) if the monitor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/monitors")
    public ResponseEntity<Monitor> updateMonitor(@Valid @RequestBody Monitor monitor) throws URISyntaxException {
        log.debug("REST request to update Monitor : {}", monitor);
        if (monitor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Monitor result = monitorService.save(monitor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, monitor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /monitors : get all the monitors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of monitors in body
     */
    @GetMapping("/monitors")
    public ResponseEntity<List<Monitor>> getAllMonitors(MonitorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Monitors by criteria: {}", criteria);
        Page<Monitor> page = monitorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/monitors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /monitors/count : count all the monitors.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/monitors/count")
    public ResponseEntity<Long> countMonitors(MonitorCriteria criteria) {
        log.debug("REST request to count Monitors by criteria: {}", criteria);
        return ResponseEntity.ok().body(monitorQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /monitors/:id : get the "id" monitor.
     *
     * @param id the id of the monitor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the monitor, or with status 404 (Not Found)
     */
    @GetMapping("/monitors/{id}")
    public ResponseEntity<Monitor> getMonitor(@PathVariable Long id) {
        log.debug("REST request to get Monitor : {}", id);
        Optional<Monitor> monitor = monitorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(monitor);
    }

    /**
     * DELETE  /monitors/:id : delete the "id" monitor.
     *
     * @param id the id of the monitor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/monitors/{id}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable Long id) {
        log.debug("REST request to delete Monitor : {}", id);
        monitorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/monitors?query=:query : search for the monitor corresponding
     * to the query.
     *
     * @param query the query of the monitor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/monitors")
    public ResponseEntity<List<Monitor>> searchMonitors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Monitors for query {}", query);
        Page<Monitor> page = monitorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/monitors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
