package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurLogiqueService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperateurLogiqueCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.OperateurLogiqueQueryService;
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
 * REST controller for managing OperateurLogique.
 */
@RestController
@RequestMapping("/api")
public class OperateurLogiqueResource {

    private final Logger log = LoggerFactory.getLogger(OperateurLogiqueResource.class);

    private static final String ENTITY_NAME = "operateurLogique";

    private final OperateurLogiqueService operateurLogiqueService;

    private final OperateurLogiqueQueryService operateurLogiqueQueryService;

    public OperateurLogiqueResource(OperateurLogiqueService operateurLogiqueService, OperateurLogiqueQueryService operateurLogiqueQueryService) {
        this.operateurLogiqueService = operateurLogiqueService;
        this.operateurLogiqueQueryService = operateurLogiqueQueryService;
    }

    /**
     * POST  /operateur-logiques : Create a new operateurLogique.
     *
     * @param operateurLogique the operateurLogique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operateurLogique, or with status 400 (Bad Request) if the operateurLogique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operateur-logiques")
    public ResponseEntity<OperateurLogique> createOperateurLogique(@Valid @RequestBody OperateurLogique operateurLogique) throws URISyntaxException {
        log.debug("REST request to save OperateurLogique : {}", operateurLogique);
        if (operateurLogique.getId() != null) {
            throw new BadRequestAlertException("A new operateurLogique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperateurLogique result = operateurLogiqueService.save(operateurLogique);
        return ResponseEntity.created(new URI("/api/operateur-logiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operateur-logiques : Updates an existing operateurLogique.
     *
     * @param operateurLogique the operateurLogique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operateurLogique,
     * or with status 400 (Bad Request) if the operateurLogique is not valid,
     * or with status 500 (Internal Server Error) if the operateurLogique couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operateur-logiques")
    public ResponseEntity<OperateurLogique> updateOperateurLogique(@Valid @RequestBody OperateurLogique operateurLogique) throws URISyntaxException {
        log.debug("REST request to update OperateurLogique : {}", operateurLogique);
        if (operateurLogique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperateurLogique result = operateurLogiqueService.save(operateurLogique);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operateurLogique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operateur-logiques : get all the operateurLogiques.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of operateurLogiques in body
     */
    @GetMapping("/operateur-logiques")
    public ResponseEntity<List<OperateurLogique>> getAllOperateurLogiques(OperateurLogiqueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OperateurLogiques by criteria: {}", criteria);
        Page<OperateurLogique> page = operateurLogiqueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operateur-logiques");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /operateur-logiques/count : count all the operateurLogiques.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/operateur-logiques/count")
    public ResponseEntity<Long> countOperateurLogiques(OperateurLogiqueCriteria criteria) {
        log.debug("REST request to count OperateurLogiques by criteria: {}", criteria);
        return ResponseEntity.ok().body(operateurLogiqueQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /operateur-logiques/:id : get the "id" operateurLogique.
     *
     * @param id the id of the operateurLogique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operateurLogique, or with status 404 (Not Found)
     */
    @GetMapping("/operateur-logiques/{id}")
    public ResponseEntity<OperateurLogique> getOperateurLogique(@PathVariable Long id) {
        log.debug("REST request to get OperateurLogique : {}", id);
        Optional<OperateurLogique> operateurLogique = operateurLogiqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operateurLogique);
    }

    /**
     * DELETE  /operateur-logiques/:id : delete the "id" operateurLogique.
     *
     * @param id the id of the operateurLogique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operateur-logiques/{id}")
    public ResponseEntity<Void> deleteOperateurLogique(@PathVariable Long id) {
        log.debug("REST request to delete OperateurLogique : {}", id);
        operateurLogiqueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operateur-logiques?query=:query : search for the operateurLogique corresponding
     * to the query.
     *
     * @param query the query of the operateurLogique search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operateur-logiques")
    public ResponseEntity<List<OperateurLogique>> searchOperateurLogiques(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OperateurLogiques for query {}", query);
        Page<OperateurLogique> page = operateurLogiqueService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operateur-logiques");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
