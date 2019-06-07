package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Fonction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FonctionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FonctionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FonctionQueryService;
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
 * REST controller for managing Fonction.
 */
@RestController
@RequestMapping("/api")
public class FonctionResource {

    private final Logger log = LoggerFactory.getLogger(FonctionResource.class);

    private static final String ENTITY_NAME = "fonction";

    private final FonctionService fonctionService;

    private final FonctionQueryService fonctionQueryService;

    public FonctionResource(FonctionService fonctionService, FonctionQueryService fonctionQueryService) {
        this.fonctionService = fonctionService;
        this.fonctionQueryService = fonctionQueryService;
    }

    /**
     * POST  /fonctions : Create a new fonction.
     *
     * @param fonction the fonction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fonction, or with status 400 (Bad Request) if the fonction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fonctions")
    public ResponseEntity<Fonction> createFonction(@Valid @RequestBody Fonction fonction) throws URISyntaxException {
        log.debug("REST request to save Fonction : {}", fonction);
        if (fonction.getId() != null) {
            throw new BadRequestAlertException("A new fonction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fonction result = fonctionService.save(fonction);
        return ResponseEntity.created(new URI("/api/fonctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fonctions : Updates an existing fonction.
     *
     * @param fonction the fonction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fonction,
     * or with status 400 (Bad Request) if the fonction is not valid,
     * or with status 500 (Internal Server Error) if the fonction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fonctions")
    public ResponseEntity<Fonction> updateFonction(@Valid @RequestBody Fonction fonction) throws URISyntaxException {
        log.debug("REST request to update Fonction : {}", fonction);
        if (fonction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fonction result = fonctionService.save(fonction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fonction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fonctions : get all the fonctions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of fonctions in body
     */
    @GetMapping("/fonctions")
    public ResponseEntity<List<Fonction>> getAllFonctions(FonctionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fonctions by criteria: {}", criteria);
        Page<Fonction> page = fonctionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fonctions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /fonctions/count : count all the fonctions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/fonctions/count")
    public ResponseEntity<Long> countFonctions(FonctionCriteria criteria) {
        log.debug("REST request to count Fonctions by criteria: {}", criteria);
        return ResponseEntity.ok().body(fonctionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /fonctions/:id : get the "id" fonction.
     *
     * @param id the id of the fonction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fonction, or with status 404 (Not Found)
     */
    @GetMapping("/fonctions/{id}")
    public ResponseEntity<Fonction> getFonction(@PathVariable Long id) {
        log.debug("REST request to get Fonction : {}", id);
        Optional<Fonction> fonction = fonctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fonction);
    }

    /**
     * DELETE  /fonctions/:id : delete the "id" fonction.
     *
     * @param id the id of the fonction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fonctions/{id}")
    public ResponseEntity<Void> deleteFonction(@PathVariable Long id) {
        log.debug("REST request to delete Fonction : {}", id);
        fonctionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fonctions?query=:query : search for the fonction corresponding
     * to the query.
     *
     * @param query the query of the fonction search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fonctions")
    public ResponseEntity<List<Fonction>> searchFonctions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fonctions for query {}", query);
        Page<Fonction> page = fonctionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fonctions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
