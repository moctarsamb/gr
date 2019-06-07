package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ClauseService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ClauseCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ClauseQueryService;
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
 * REST controller for managing Clause.
 */
@RestController
@RequestMapping("/api")
public class ClauseResource {

    private final Logger log = LoggerFactory.getLogger(ClauseResource.class);

    private static final String ENTITY_NAME = "clause";

    private final ClauseService clauseService;

    private final ClauseQueryService clauseQueryService;

    public ClauseResource(ClauseService clauseService, ClauseQueryService clauseQueryService) {
        this.clauseService = clauseService;
        this.clauseQueryService = clauseQueryService;
    }

    /**
     * POST  /clauses : Create a new clause.
     *
     * @param clause the clause to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clause, or with status 400 (Bad Request) if the clause has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clauses")
    public ResponseEntity<Clause> createClause(@RequestBody Clause clause) throws URISyntaxException {
        log.debug("REST request to save Clause : {}", clause);
        if (clause.getId() != null) {
            throw new BadRequestAlertException("A new clause cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clause result = clauseService.save(clause);
        return ResponseEntity.created(new URI("/api/clauses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clauses : Updates an existing clause.
     *
     * @param clause the clause to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clause,
     * or with status 400 (Bad Request) if the clause is not valid,
     * or with status 500 (Internal Server Error) if the clause couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clauses")
    public ResponseEntity<Clause> updateClause(@RequestBody Clause clause) throws URISyntaxException {
        log.debug("REST request to update Clause : {}", clause);
        if (clause.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Clause result = clauseService.save(clause);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clause.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clauses : get all the clauses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clauses in body
     */
    @GetMapping("/clauses")
    public ResponseEntity<List<Clause>> getAllClauses(ClauseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Clauses by criteria: {}", criteria);
        Page<Clause> page = clauseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clauses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /clauses/count : count all the clauses.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/clauses/count")
    public ResponseEntity<Long> countClauses(ClauseCriteria criteria) {
        log.debug("REST request to count Clauses by criteria: {}", criteria);
        return ResponseEntity.ok().body(clauseQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /clauses/:id : get the "id" clause.
     *
     * @param id the id of the clause to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clause, or with status 404 (Not Found)
     */
    @GetMapping("/clauses/{id}")
    public ResponseEntity<Clause> getClause(@PathVariable Long id) {
        log.debug("REST request to get Clause : {}", id);
        Optional<Clause> clause = clauseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clause);
    }

    /**
     * DELETE  /clauses/:id : delete the "id" clause.
     *
     * @param id the id of the clause to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clauses/{id}")
    public ResponseEntity<Void> deleteClause(@PathVariable Long id) {
        log.debug("REST request to delete Clause : {}", id);
        clauseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/clauses?query=:query : search for the clause corresponding
     * to the query.
     *
     * @param query the query of the clause search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clauses")
    public ResponseEntity<List<Clause>> searchClauses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clauses for query {}", query);
        Page<Clause> page = clauseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clauses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
