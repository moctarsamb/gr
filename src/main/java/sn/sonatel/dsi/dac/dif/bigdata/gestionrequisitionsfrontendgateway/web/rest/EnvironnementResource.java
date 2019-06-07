package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvironnementService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.EnvironnementCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvironnementQueryService;
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
 * REST controller for managing Environnement.
 */
@RestController
@RequestMapping("/api")
public class EnvironnementResource {

    private final Logger log = LoggerFactory.getLogger(EnvironnementResource.class);

    private static final String ENTITY_NAME = "environnement";

    private final EnvironnementService environnementService;

    private final EnvironnementQueryService environnementQueryService;

    public EnvironnementResource(EnvironnementService environnementService, EnvironnementQueryService environnementQueryService) {
        this.environnementService = environnementService;
        this.environnementQueryService = environnementQueryService;
    }

    /**
     * POST  /environnements : Create a new environnement.
     *
     * @param environnement the environnement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new environnement, or with status 400 (Bad Request) if the environnement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/environnements")
    public ResponseEntity<Environnement> createEnvironnement(@Valid @RequestBody Environnement environnement) throws URISyntaxException {
        log.debug("REST request to save Environnement : {}", environnement);
        if (environnement.getId() != null) {
            throw new BadRequestAlertException("A new environnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Environnement result = environnementService.save(environnement);
        return ResponseEntity.created(new URI("/api/environnements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /environnements : Updates an existing environnement.
     *
     * @param environnement the environnement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated environnement,
     * or with status 400 (Bad Request) if the environnement is not valid,
     * or with status 500 (Internal Server Error) if the environnement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/environnements")
    public ResponseEntity<Environnement> updateEnvironnement(@Valid @RequestBody Environnement environnement) throws URISyntaxException {
        log.debug("REST request to update Environnement : {}", environnement);
        if (environnement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Environnement result = environnementService.save(environnement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, environnement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /environnements : get all the environnements.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of environnements in body
     */
    @GetMapping("/environnements")
    public ResponseEntity<List<Environnement>> getAllEnvironnements(EnvironnementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Environnements by criteria: {}", criteria);
        Page<Environnement> page = environnementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/environnements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /environnements/count : count all the environnements.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/environnements/count")
    public ResponseEntity<Long> countEnvironnements(EnvironnementCriteria criteria) {
        log.debug("REST request to count Environnements by criteria: {}", criteria);
        return ResponseEntity.ok().body(environnementQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /environnements/:id : get the "id" environnement.
     *
     * @param id the id of the environnement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the environnement, or with status 404 (Not Found)
     */
    @GetMapping("/environnements/{id}")
    public ResponseEntity<Environnement> getEnvironnement(@PathVariable Long id) {
        log.debug("REST request to get Environnement : {}", id);
        Optional<Environnement> environnement = environnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(environnement);
    }

    /**
     * DELETE  /environnements/:id : delete the "id" environnement.
     *
     * @param id the id of the environnement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/environnements/{id}")
    public ResponseEntity<Void> deleteEnvironnement(@PathVariable Long id) {
        log.debug("REST request to delete Environnement : {}", id);
        environnementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/environnements?query=:query : search for the environnement corresponding
     * to the query.
     *
     * @param query the query of the environnement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/environnements")
    public ResponseEntity<List<Environnement>> searchEnvironnements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Environnements for query {}", query);
        Page<Environnement> page = environnementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/environnements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
