package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.CritereService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.CritereCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.CritereQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Critere.
 */
@RestController
@RequestMapping("/api")
public class CritereResource {

    private final Logger log = LoggerFactory.getLogger(CritereResource.class);

    private static final String ENTITY_NAME = "critere";

    private final CritereService critereService;

    private final CritereQueryService critereQueryService;

    public CritereResource(CritereService critereService, CritereQueryService critereQueryService) {
        this.critereService = critereService;
        this.critereQueryService = critereQueryService;
    }

    /**
     * POST  /criteres : Create a new critere.
     *
     * @param critere the critere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new critere, or with status 400 (Bad Request) if the critere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/criteres")
    public ResponseEntity<Critere> createCritere(@RequestBody Critere critere) throws URISyntaxException {
        log.debug("REST request to save Critere : {}", critere);
        if (critere.getId() != null) {
            throw new BadRequestAlertException("A new critere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Critere result = critereService.save(critere);
        return ResponseEntity.created(new URI("/api/criteres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /criteres : Updates an existing critere.
     *
     * @param critere the critere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated critere,
     * or with status 400 (Bad Request) if the critere is not valid,
     * or with status 500 (Internal Server Error) if the critere couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/criteres")
    public ResponseEntity<Critere> updateCritere(@RequestBody Critere critere) throws URISyntaxException {
        log.debug("REST request to update Critere : {}", critere);
        if (critere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Critere result = critereService.save(critere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, critere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /criteres : get all the criteres.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of criteres in body
     */
    @GetMapping("/criteres")
    public ResponseEntity<List<Critere>> getAllCriteres(CritereCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Criteres by criteria: {}", criteria);
        Page<Critere> page = critereQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/criteres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /criteres/count : count all the criteres.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/criteres/count")
    public ResponseEntity<Long> countCriteres(CritereCriteria criteria) {
        log.debug("REST request to count Criteres by criteria: {}", criteria);
        return ResponseEntity.ok().body(critereQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /criteres/:id : get the "id" critere.
     *
     * @param id the id of the critere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the critere, or with status 404 (Not Found)
     */
    @GetMapping("/criteres/{id}")
    public ResponseEntity<Critere> getCritere(@PathVariable Long id) {
        log.debug("REST request to get Critere : {}", id);
        Optional<Critere> critere = critereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(critere);
    }

    /**
     * DELETE  /criteres/:id : delete the "id" critere.
     *
     * @param id the id of the critere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/criteres/{id}")
    public ResponseEntity<Void> deleteCritere(@PathVariable Long id) {
        log.debug("REST request to delete Critere : {}", id);
        critereService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/criteres?query=:query : search for the critere corresponding
     * to the query.
     *
     * @param query the query of the critere search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/criteres")
    public ResponseEntity<List<Critere>> searchCriteres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Criteres for query {}", query);
        Page<Critere> page = critereService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/criteres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
