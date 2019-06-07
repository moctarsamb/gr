package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Valeur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ValeurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ValeurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ValeurQueryService;
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
 * REST controller for managing Valeur.
 */
@RestController
@RequestMapping("/api")
public class ValeurResource {

    private final Logger log = LoggerFactory.getLogger(ValeurResource.class);

    private static final String ENTITY_NAME = "valeur";

    private final ValeurService valeurService;

    private final ValeurQueryService valeurQueryService;

    public ValeurResource(ValeurService valeurService, ValeurQueryService valeurQueryService) {
        this.valeurService = valeurService;
        this.valeurQueryService = valeurQueryService;
    }

    /**
     * POST  /valeurs : Create a new valeur.
     *
     * @param valeur the valeur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valeur, or with status 400 (Bad Request) if the valeur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valeurs")
    public ResponseEntity<Valeur> createValeur(@Valid @RequestBody Valeur valeur) throws URISyntaxException {
        log.debug("REST request to save Valeur : {}", valeur);
        if (valeur.getId() != null) {
            throw new BadRequestAlertException("A new valeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Valeur result = valeurService.save(valeur);
        return ResponseEntity.created(new URI("/api/valeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valeurs : Updates an existing valeur.
     *
     * @param valeur the valeur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valeur,
     * or with status 400 (Bad Request) if the valeur is not valid,
     * or with status 500 (Internal Server Error) if the valeur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valeurs")
    public ResponseEntity<Valeur> updateValeur(@Valid @RequestBody Valeur valeur) throws URISyntaxException {
        log.debug("REST request to update Valeur : {}", valeur);
        if (valeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Valeur result = valeurService.save(valeur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valeur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valeurs : get all the valeurs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of valeurs in body
     */
    @GetMapping("/valeurs")
    public ResponseEntity<List<Valeur>> getAllValeurs(ValeurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Valeurs by criteria: {}", criteria);
        Page<Valeur> page = valeurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/valeurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /valeurs/count : count all the valeurs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/valeurs/count")
    public ResponseEntity<Long> countValeurs(ValeurCriteria criteria) {
        log.debug("REST request to count Valeurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(valeurQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /valeurs/:id : get the "id" valeur.
     *
     * @param id the id of the valeur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valeur, or with status 404 (Not Found)
     */
    @GetMapping("/valeurs/{id}")
    public ResponseEntity<Valeur> getValeur(@PathVariable Long id) {
        log.debug("REST request to get Valeur : {}", id);
        Optional<Valeur> valeur = valeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(valeur);
    }

    /**
     * DELETE  /valeurs/:id : delete the "id" valeur.
     *
     * @param id the id of the valeur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valeurs/{id}")
    public ResponseEntity<Void> deleteValeur(@PathVariable Long id) {
        log.debug("REST request to delete Valeur : {}", id);
        valeurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/valeurs?query=:query : search for the valeur corresponding
     * to the query.
     *
     * @param query the query of the valeur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/valeurs")
    public ResponseEntity<List<Valeur>> searchValeurs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Valeurs for query {}", query);
        Page<Valeur> page = valeurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/valeurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
