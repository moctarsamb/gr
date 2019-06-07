package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperateurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurQueryService;
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
 * REST controller for managing Operateur.
 */
@RestController
@RequestMapping("/api")
public class OperateurResource {

    private final Logger log = LoggerFactory.getLogger(OperateurResource.class);

    private static final String ENTITY_NAME = "operateur";

    private final OperateurService operateurService;

    private final OperateurQueryService operateurQueryService;

    public OperateurResource(OperateurService operateurService, OperateurQueryService operateurQueryService) {
        this.operateurService = operateurService;
        this.operateurQueryService = operateurQueryService;
    }

    /**
     * POST  /operateurs : Create a new operateur.
     *
     * @param operateur the operateur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operateur, or with status 400 (Bad Request) if the operateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operateurs")
    public ResponseEntity<Operateur> createOperateur(@Valid @RequestBody Operateur operateur) throws URISyntaxException {
        log.debug("REST request to save Operateur : {}", operateur);
        if (operateur.getId() != null) {
            throw new BadRequestAlertException("A new operateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Operateur result = operateurService.save(operateur);
        return ResponseEntity.created(new URI("/api/operateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operateurs : Updates an existing operateur.
     *
     * @param operateur the operateur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operateur,
     * or with status 400 (Bad Request) if the operateur is not valid,
     * or with status 500 (Internal Server Error) if the operateur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operateurs")
    public ResponseEntity<Operateur> updateOperateur(@Valid @RequestBody Operateur operateur) throws URISyntaxException {
        log.debug("REST request to update Operateur : {}", operateur);
        if (operateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Operateur result = operateurService.save(operateur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operateur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operateurs : get all the operateurs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of operateurs in body
     */
    @GetMapping("/operateurs")
    public ResponseEntity<List<Operateur>> getAllOperateurs(OperateurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Operateurs by criteria: {}", criteria);
        Page<Operateur> page = operateurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operateurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /operateurs/count : count all the operateurs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/operateurs/count")
    public ResponseEntity<Long> countOperateurs(OperateurCriteria criteria) {
        log.debug("REST request to count Operateurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(operateurQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /operateurs/:id : get the "id" operateur.
     *
     * @param id the id of the operateur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operateur, or with status 404 (Not Found)
     */
    @GetMapping("/operateurs/{id}")
    public ResponseEntity<Operateur> getOperateur(@PathVariable Long id) {
        log.debug("REST request to get Operateur : {}", id);
        Optional<Operateur> operateur = operateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operateur);
    }

    /**
     * DELETE  /operateurs/:id : delete the "id" operateur.
     *
     * @param id the id of the operateur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operateurs/{id}")
    public ResponseEntity<Void> deleteOperateur(@PathVariable Long id) {
        log.debug("REST request to delete Operateur : {}", id);
        operateurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operateurs?query=:query : search for the operateur corresponding
     * to the query.
     *
     * @param query the query of the operateur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operateurs")
    public ResponseEntity<List<Operateur>> searchOperateurs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Operateurs for query {}", query);
        Page<Operateur> page = operateurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operateurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
