package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperandeService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperandeCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperandeQueryService;
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
 * REST controller for managing Operande.
 */
@RestController
@RequestMapping("/api")
public class OperandeResource {

    private final Logger log = LoggerFactory.getLogger(OperandeResource.class);

    private static final String ENTITY_NAME = "operande";

    private final OperandeService operandeService;

    private final OperandeQueryService operandeQueryService;

    public OperandeResource(OperandeService operandeService, OperandeQueryService operandeQueryService) {
        this.operandeService = operandeService;
        this.operandeQueryService = operandeQueryService;
    }

    /**
     * POST  /operandes : Create a new operande.
     *
     * @param operande the operande to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operande, or with status 400 (Bad Request) if the operande has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operandes")
    public ResponseEntity<Operande> createOperande(@RequestBody Operande operande) throws URISyntaxException {
        log.debug("REST request to save Operande : {}", operande);
        if (operande.getId() != null) {
            throw new BadRequestAlertException("A new operande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Operande result = operandeService.save(operande);
        return ResponseEntity.created(new URI("/api/operandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operandes : Updates an existing operande.
     *
     * @param operande the operande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operande,
     * or with status 400 (Bad Request) if the operande is not valid,
     * or with status 500 (Internal Server Error) if the operande couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operandes")
    public ResponseEntity<Operande> updateOperande(@RequestBody Operande operande) throws URISyntaxException {
        log.debug("REST request to update Operande : {}", operande);
        if (operande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Operande result = operandeService.save(operande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operande.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operandes : get all the operandes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of operandes in body
     */
    @GetMapping("/operandes")
    public ResponseEntity<List<Operande>> getAllOperandes(OperandeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Operandes by criteria: {}", criteria);
        Page<Operande> page = operandeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operandes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /operandes/count : count all the operandes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/operandes/count")
    public ResponseEntity<Long> countOperandes(OperandeCriteria criteria) {
        log.debug("REST request to count Operandes by criteria: {}", criteria);
        return ResponseEntity.ok().body(operandeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /operandes/:id : get the "id" operande.
     *
     * @param id the id of the operande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operande, or with status 404 (Not Found)
     */
    @GetMapping("/operandes/{id}")
    public ResponseEntity<Operande> getOperande(@PathVariable Long id) {
        log.debug("REST request to get Operande : {}", id);
        Optional<Operande> operande = operandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operande);
    }

    /**
     * DELETE  /operandes/:id : delete the "id" operande.
     *
     * @param id the id of the operande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operandes/{id}")
    public ResponseEntity<Void> deleteOperande(@PathVariable Long id) {
        log.debug("REST request to delete Operande : {}", id);
        operandeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operandes?query=:query : search for the operande corresponding
     * to the query.
     *
     * @param query the query of the operande search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operandes")
    public ResponseEntity<List<Operande>> searchOperandes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Operandes for query {}", query);
        Page<Operande> page = operandeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operandes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
