package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.RequisitionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.RequisitionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.RequisitionQueryService;
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
 * REST controller for managing Requisition.
 */
@RestController
@RequestMapping("/api")
public class RequisitionResource {

    private final Logger log = LoggerFactory.getLogger(RequisitionResource.class);

    private static final String ENTITY_NAME = "requisition";

    private final RequisitionService requisitionService;

    private final RequisitionQueryService requisitionQueryService;

    public RequisitionResource(RequisitionService requisitionService, RequisitionQueryService requisitionQueryService) {
        this.requisitionService = requisitionService;
        this.requisitionQueryService = requisitionQueryService;
    }

    /**
     * POST  /requisitions : Create a new requisition.
     *
     * @param requisition the requisition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requisition, or with status 400 (Bad Request) if the requisition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requisitions")
    public ResponseEntity<Requisition> createRequisition(@Valid @RequestBody Requisition requisition) throws URISyntaxException {
        log.debug("REST request to save Requisition : {}", requisition);
        if (requisition.getId() != null) {
            throw new BadRequestAlertException("A new requisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Requisition result = requisitionService.save(requisition);
        return ResponseEntity.created(new URI("/api/requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requisitions : Updates an existing requisition.
     *
     * @param requisition the requisition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requisition,
     * or with status 400 (Bad Request) if the requisition is not valid,
     * or with status 500 (Internal Server Error) if the requisition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requisitions")
    public ResponseEntity<Requisition> updateRequisition(@Valid @RequestBody Requisition requisition) throws URISyntaxException {
        log.debug("REST request to update Requisition : {}", requisition);
        if (requisition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Requisition result = requisitionService.save(requisition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requisition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requisitions : get all the requisitions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of requisitions in body
     */
    @GetMapping("/requisitions")
    public ResponseEntity<List<Requisition>> getAllRequisitions(RequisitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Requisitions by criteria: {}", criteria);
        Page<Requisition> page = requisitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requisitions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /requisitions/count : count all the requisitions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/requisitions/count")
    public ResponseEntity<Long> countRequisitions(RequisitionCriteria criteria) {
        log.debug("REST request to count Requisitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(requisitionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /requisitions/:id : get the "id" requisition.
     *
     * @param id the id of the requisition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requisition, or with status 404 (Not Found)
     */
    @GetMapping("/requisitions/{id}")
    public ResponseEntity<Requisition> getRequisition(@PathVariable Long id) {
        log.debug("REST request to get Requisition : {}", id);
        Optional<Requisition> requisition = requisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisition);
    }

    /**
     * DELETE  /requisitions/:id : delete the "id" requisition.
     *
     * @param id the id of the requisition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requisitions/{id}")
    public ResponseEntity<Void> deleteRequisition(@PathVariable Long id) {
        log.debug("REST request to delete Requisition : {}", id);
        requisitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/requisitions?query=:query : search for the requisition corresponding
     * to the query.
     *
     * @param query the query of the requisition search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/requisitions")
    public ResponseEntity<List<Requisition>> searchRequisitions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Requisitions for query {}", query);
        Page<Requisition> page = requisitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/requisitions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
