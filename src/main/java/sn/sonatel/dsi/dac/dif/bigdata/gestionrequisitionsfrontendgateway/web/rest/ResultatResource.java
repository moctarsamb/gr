package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Resultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ResultatService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ResultatCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ResultatQueryService;
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
 * REST controller for managing Resultat.
 */
@RestController
@RequestMapping("/api")
public class ResultatResource {

    private final Logger log = LoggerFactory.getLogger(ResultatResource.class);

    private static final String ENTITY_NAME = "resultat";

    private final ResultatService resultatService;

    private final ResultatQueryService resultatQueryService;

    public ResultatResource(ResultatService resultatService, ResultatQueryService resultatQueryService) {
        this.resultatService = resultatService;
        this.resultatQueryService = resultatQueryService;
    }

    /**
     * POST  /resultats : Create a new resultat.
     *
     * @param resultat the resultat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultat, or with status 400 (Bad Request) if the resultat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultats")
    public ResponseEntity<Resultat> createResultat(@Valid @RequestBody Resultat resultat) throws URISyntaxException {
        log.debug("REST request to save Resultat : {}", resultat);
        if (resultat.getId() != null) {
            throw new BadRequestAlertException("A new resultat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resultat result = resultatService.save(resultat);
        return ResponseEntity.created(new URI("/api/resultats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultats : Updates an existing resultat.
     *
     * @param resultat the resultat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultat,
     * or with status 400 (Bad Request) if the resultat is not valid,
     * or with status 500 (Internal Server Error) if the resultat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultats")
    public ResponseEntity<Resultat> updateResultat(@Valid @RequestBody Resultat resultat) throws URISyntaxException {
        log.debug("REST request to update Resultat : {}", resultat);
        if (resultat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Resultat result = resultatService.save(resultat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultats : get all the resultats.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of resultats in body
     */
    @GetMapping("/resultats")
    public ResponseEntity<List<Resultat>> getAllResultats(ResultatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Resultats by criteria: {}", criteria);
        Page<Resultat> page = resultatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /resultats/count : count all the resultats.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/resultats/count")
    public ResponseEntity<Long> countResultats(ResultatCriteria criteria) {
        log.debug("REST request to count Resultats by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultatQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /resultats/:id : get the "id" resultat.
     *
     * @param id the id of the resultat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultat, or with status 404 (Not Found)
     */
    @GetMapping("/resultats/{id}")
    public ResponseEntity<Resultat> getResultat(@PathVariable Long id) {
        log.debug("REST request to get Resultat : {}", id);
        Optional<Resultat> resultat = resultatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultat);
    }

    /**
     * DELETE  /resultats/:id : delete the "id" resultat.
     *
     * @param id the id of the resultat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultats/{id}")
    public ResponseEntity<Void> deleteResultat(@PathVariable Long id) {
        log.debug("REST request to delete Resultat : {}", id);
        resultatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/resultats?query=:query : search for the resultat corresponding
     * to the query.
     *
     * @param query the query of the resultat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/resultats")
    public ResponseEntity<List<Resultat>> searchResultats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Resultats for query {}", query);
        Page<Resultat> page = resultatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
