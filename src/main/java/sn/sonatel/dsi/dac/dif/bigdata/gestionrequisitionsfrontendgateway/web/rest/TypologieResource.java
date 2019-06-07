package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Typologie;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypologieService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypologieCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypologieQueryService;
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
 * REST controller for managing Typologie.
 */
@RestController
@RequestMapping("/api")
public class TypologieResource {

    private final Logger log = LoggerFactory.getLogger(TypologieResource.class);

    private static final String ENTITY_NAME = "typologie";

    private final TypologieService typologieService;

    private final TypologieQueryService typologieQueryService;

    public TypologieResource(TypologieService typologieService, TypologieQueryService typologieQueryService) {
        this.typologieService = typologieService;
        this.typologieQueryService = typologieQueryService;
    }

    /**
     * POST  /typologies : Create a new typologie.
     *
     * @param typologie the typologie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typologie, or with status 400 (Bad Request) if the typologie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/typologies")
    public ResponseEntity<Typologie> createTypologie(@Valid @RequestBody Typologie typologie) throws URISyntaxException {
        log.debug("REST request to save Typologie : {}", typologie);
        if (typologie.getId() != null) {
            throw new BadRequestAlertException("A new typologie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Typologie result = typologieService.save(typologie);
        return ResponseEntity.created(new URI("/api/typologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /typologies : Updates an existing typologie.
     *
     * @param typologie the typologie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typologie,
     * or with status 400 (Bad Request) if the typologie is not valid,
     * or with status 500 (Internal Server Error) if the typologie couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/typologies")
    public ResponseEntity<Typologie> updateTypologie(@Valid @RequestBody Typologie typologie) throws URISyntaxException {
        log.debug("REST request to update Typologie : {}", typologie);
        if (typologie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Typologie result = typologieService.save(typologie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typologie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /typologies : get all the typologies.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typologies in body
     */
    @GetMapping("/typologies")
    public ResponseEntity<List<Typologie>> getAllTypologies(TypologieCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Typologies by criteria: {}", criteria);
        Page<Typologie> page = typologieQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/typologies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /typologies/count : count all the typologies.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/typologies/count")
    public ResponseEntity<Long> countTypologies(TypologieCriteria criteria) {
        log.debug("REST request to count Typologies by criteria: {}", criteria);
        return ResponseEntity.ok().body(typologieQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /typologies/:id : get the "id" typologie.
     *
     * @param id the id of the typologie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typologie, or with status 404 (Not Found)
     */
    @GetMapping("/typologies/{id}")
    public ResponseEntity<Typologie> getTypologie(@PathVariable Long id) {
        log.debug("REST request to get Typologie : {}", id);
        Optional<Typologie> typologie = typologieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typologie);
    }

    /**
     * DELETE  /typologies/:id : delete the "id" typologie.
     *
     * @param id the id of the typologie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/typologies/{id}")
    public ResponseEntity<Void> deleteTypologie(@PathVariable Long id) {
        log.debug("REST request to delete Typologie : {}", id);
        typologieService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/typologies?query=:query : search for the typologie corresponding
     * to the query.
     *
     * @param query the query of the typologie search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/typologies")
    public ResponseEntity<List<Typologie>> searchTypologies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Typologies for query {}", query);
        Page<Typologie> page = typologieService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/typologies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
