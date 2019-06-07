package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ColonneService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ColonneCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ColonneQueryService;
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
 * REST controller for managing Colonne.
 */
@RestController
@RequestMapping("/api")
public class ColonneResource {

    private final Logger log = LoggerFactory.getLogger(ColonneResource.class);

    private static final String ENTITY_NAME = "colonne";

    private final ColonneService colonneService;

    private final ColonneQueryService colonneQueryService;

    public ColonneResource(ColonneService colonneService, ColonneQueryService colonneQueryService) {
        this.colonneService = colonneService;
        this.colonneQueryService = colonneQueryService;
    }

    /**
     * POST  /colonnes : Create a new colonne.
     *
     * @param colonne the colonne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new colonne, or with status 400 (Bad Request) if the colonne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/colonnes")
    public ResponseEntity<Colonne> createColonne(@Valid @RequestBody Colonne colonne) throws URISyntaxException {
        log.debug("REST request to save Colonne : {}", colonne);
        if (colonne.getId() != null) {
            throw new BadRequestAlertException("A new colonne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Colonne result = colonneService.save(colonne);
        return ResponseEntity.created(new URI("/api/colonnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /colonnes : Updates an existing colonne.
     *
     * @param colonne the colonne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated colonne,
     * or with status 400 (Bad Request) if the colonne is not valid,
     * or with status 500 (Internal Server Error) if the colonne couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/colonnes")
    public ResponseEntity<Colonne> updateColonne(@Valid @RequestBody Colonne colonne) throws URISyntaxException {
        log.debug("REST request to update Colonne : {}", colonne);
        if (colonne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Colonne result = colonneService.save(colonne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, colonne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /colonnes : get all the colonnes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of colonnes in body
     */
    @GetMapping("/colonnes")
    public ResponseEntity<List<Colonne>> getAllColonnes(ColonneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Colonnes by criteria: {}", criteria);
        Page<Colonne> page = colonneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/colonnes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /colonnes/count : count all the colonnes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/colonnes/count")
    public ResponseEntity<Long> countColonnes(ColonneCriteria criteria) {
        log.debug("REST request to count Colonnes by criteria: {}", criteria);
        return ResponseEntity.ok().body(colonneQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /colonnes/:id : get the "id" colonne.
     *
     * @param id the id of the colonne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the colonne, or with status 404 (Not Found)
     */
    @GetMapping("/colonnes/{id}")
    public ResponseEntity<Colonne> getColonne(@PathVariable Long id) {
        log.debug("REST request to get Colonne : {}", id);
        Optional<Colonne> colonne = colonneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colonne);
    }

    /**
     * DELETE  /colonnes/:id : delete the "id" colonne.
     *
     * @param id the id of the colonne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/colonnes/{id}")
    public ResponseEntity<Void> deleteColonne(@PathVariable Long id) {
        log.debug("REST request to delete Colonne : {}", id);
        colonneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/colonnes?query=:query : search for the colonne corresponding
     * to the query.
     *
     * @param query the query of the colonne search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/colonnes")
    public ResponseEntity<List<Colonne>> searchColonnes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Colonnes for query {}", query);
        Page<Colonne> page = colonneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/colonnes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
