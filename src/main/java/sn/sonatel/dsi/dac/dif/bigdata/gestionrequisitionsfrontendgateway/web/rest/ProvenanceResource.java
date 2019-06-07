package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Provenance;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProvenanceService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ProvenanceCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProvenanceQueryService;
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
 * REST controller for managing Provenance.
 */
@RestController
@RequestMapping("/api")
public class ProvenanceResource {

    private final Logger log = LoggerFactory.getLogger(ProvenanceResource.class);

    private static final String ENTITY_NAME = "provenance";

    private final ProvenanceService provenanceService;

    private final ProvenanceQueryService provenanceQueryService;

    public ProvenanceResource(ProvenanceService provenanceService, ProvenanceQueryService provenanceQueryService) {
        this.provenanceService = provenanceService;
        this.provenanceQueryService = provenanceQueryService;
    }

    /**
     * POST  /provenances : Create a new provenance.
     *
     * @param provenance the provenance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provenance, or with status 400 (Bad Request) if the provenance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provenances")
    public ResponseEntity<Provenance> createProvenance(@Valid @RequestBody Provenance provenance) throws URISyntaxException {
        log.debug("REST request to save Provenance : {}", provenance);
        if (provenance.getId() != null) {
            throw new BadRequestAlertException("A new provenance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Provenance result = provenanceService.save(provenance);
        return ResponseEntity.created(new URI("/api/provenances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provenances : Updates an existing provenance.
     *
     * @param provenance the provenance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provenance,
     * or with status 400 (Bad Request) if the provenance is not valid,
     * or with status 500 (Internal Server Error) if the provenance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provenances")
    public ResponseEntity<Provenance> updateProvenance(@Valid @RequestBody Provenance provenance) throws URISyntaxException {
        log.debug("REST request to update Provenance : {}", provenance);
        if (provenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Provenance result = provenanceService.save(provenance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, provenance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provenances : get all the provenances.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of provenances in body
     */
    @GetMapping("/provenances")
    public ResponseEntity<List<Provenance>> getAllProvenances(ProvenanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Provenances by criteria: {}", criteria);
        Page<Provenance> page = provenanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provenances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /provenances/count : count all the provenances.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/provenances/count")
    public ResponseEntity<Long> countProvenances(ProvenanceCriteria criteria) {
        log.debug("REST request to count Provenances by criteria: {}", criteria);
        return ResponseEntity.ok().body(provenanceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /provenances/:id : get the "id" provenance.
     *
     * @param id the id of the provenance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provenance, or with status 404 (Not Found)
     */
    @GetMapping("/provenances/{id}")
    public ResponseEntity<Provenance> getProvenance(@PathVariable Long id) {
        log.debug("REST request to get Provenance : {}", id);
        Optional<Provenance> provenance = provenanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provenance);
    }

    /**
     * DELETE  /provenances/:id : delete the "id" provenance.
     *
     * @param id the id of the provenance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provenances/{id}")
    public ResponseEntity<Void> deleteProvenance(@PathVariable Long id) {
        log.debug("REST request to delete Provenance : {}", id);
        provenanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/provenances?query=:query : search for the provenance corresponding
     * to the query.
     *
     * @param query the query of the provenance search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/provenances")
    public ResponseEntity<List<Provenance>> searchProvenances(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Provenances for query {}", query);
        Page<Provenance> page = provenanceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/provenances");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
