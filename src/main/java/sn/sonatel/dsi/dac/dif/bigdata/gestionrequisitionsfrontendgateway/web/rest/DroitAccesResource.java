package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.DroitAcces;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DroitAccesService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.DroitAccesCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DroitAccesQueryService;
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
 * REST controller for managing DroitAcces.
 */
@RestController
@RequestMapping("/api")
public class DroitAccesResource {

    private final Logger log = LoggerFactory.getLogger(DroitAccesResource.class);

    private static final String ENTITY_NAME = "droitAcces";

    private final DroitAccesService droitAccesService;

    private final DroitAccesQueryService droitAccesQueryService;

    public DroitAccesResource(DroitAccesService droitAccesService, DroitAccesQueryService droitAccesQueryService) {
        this.droitAccesService = droitAccesService;
        this.droitAccesQueryService = droitAccesQueryService;
    }

    /**
     * POST  /droit-acces : Create a new droitAcces.
     *
     * @param droitAcces the droitAcces to create
     * @return the ResponseEntity with status 201 (Created) and with body the new droitAcces, or with status 400 (Bad Request) if the droitAcces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/droit-acces")
    public ResponseEntity<DroitAcces> createDroitAcces(@Valid @RequestBody DroitAcces droitAcces) throws URISyntaxException {
        log.debug("REST request to save DroitAcces : {}", droitAcces);
        if (droitAcces.getId() != null) {
            throw new BadRequestAlertException("A new droitAcces cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitAcces result = droitAccesService.save(droitAcces);
        return ResponseEntity.created(new URI("/api/droit-acces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /droit-acces : Updates an existing droitAcces.
     *
     * @param droitAcces the droitAcces to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated droitAcces,
     * or with status 400 (Bad Request) if the droitAcces is not valid,
     * or with status 500 (Internal Server Error) if the droitAcces couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/droit-acces")
    public ResponseEntity<DroitAcces> updateDroitAcces(@Valid @RequestBody DroitAcces droitAcces) throws URISyntaxException {
        log.debug("REST request to update DroitAcces : {}", droitAcces);
        if (droitAcces.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DroitAcces result = droitAccesService.save(droitAcces);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, droitAcces.getId().toString()))
            .body(result);
    }

    /**
     * GET  /droit-acces : get all the droitAcces.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of droitAcces in body
     */
    @GetMapping("/droit-acces")
    public ResponseEntity<List<DroitAcces>> getAllDroitAcces(DroitAccesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DroitAcces by criteria: {}", criteria);
        Page<DroitAcces> page = droitAccesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/droit-acces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /droit-acces/count : count all the droitAcces.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/droit-acces/count")
    public ResponseEntity<Long> countDroitAcces(DroitAccesCriteria criteria) {
        log.debug("REST request to count DroitAcces by criteria: {}", criteria);
        return ResponseEntity.ok().body(droitAccesQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /droit-acces/:id : get the "id" droitAcces.
     *
     * @param id the id of the droitAcces to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the droitAcces, or with status 404 (Not Found)
     */
    @GetMapping("/droit-acces/{id}")
    public ResponseEntity<DroitAcces> getDroitAcces(@PathVariable Long id) {
        log.debug("REST request to get DroitAcces : {}", id);
        Optional<DroitAcces> droitAcces = droitAccesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitAcces);
    }

    /**
     * DELETE  /droit-acces/:id : delete the "id" droitAcces.
     *
     * @param id the id of the droitAcces to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/droit-acces/{id}")
    public ResponseEntity<Void> deleteDroitAcces(@PathVariable Long id) {
        log.debug("REST request to delete DroitAcces : {}", id);
        droitAccesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/droit-acces?query=:query : search for the droitAcces corresponding
     * to the query.
     *
     * @param query the query of the droitAcces search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/droit-acces")
    public ResponseEntity<List<DroitAcces>> searchDroitAcces(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DroitAcces for query {}", query);
        Page<DroitAcces> page = droitAccesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/droit-acces");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
