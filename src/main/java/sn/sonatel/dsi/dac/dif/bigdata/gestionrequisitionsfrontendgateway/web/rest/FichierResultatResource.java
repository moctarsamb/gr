package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FichierResultatService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FichierResultatCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FichierResultatQueryService;
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
 * REST controller for managing FichierResultat.
 */
@RestController
@RequestMapping("/api")
public class FichierResultatResource {

    private final Logger log = LoggerFactory.getLogger(FichierResultatResource.class);

    private static final String ENTITY_NAME = "fichierResultat";

    private final FichierResultatService fichierResultatService;

    private final FichierResultatQueryService fichierResultatQueryService;

    public FichierResultatResource(FichierResultatService fichierResultatService, FichierResultatQueryService fichierResultatQueryService) {
        this.fichierResultatService = fichierResultatService;
        this.fichierResultatQueryService = fichierResultatQueryService;
    }

    /**
     * POST  /fichier-resultats : Create a new fichierResultat.
     *
     * @param fichierResultat the fichierResultat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichierResultat, or with status 400 (Bad Request) if the fichierResultat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichier-resultats")
    public ResponseEntity<FichierResultat> createFichierResultat(@Valid @RequestBody FichierResultat fichierResultat) throws URISyntaxException {
        log.debug("REST request to save FichierResultat : {}", fichierResultat);
        if (fichierResultat.getId() != null) {
            throw new BadRequestAlertException("A new fichierResultat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichierResultat result = fichierResultatService.save(fichierResultat);
        return ResponseEntity.created(new URI("/api/fichier-resultats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fichier-resultats : Updates an existing fichierResultat.
     *
     * @param fichierResultat the fichierResultat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichierResultat,
     * or with status 400 (Bad Request) if the fichierResultat is not valid,
     * or with status 500 (Internal Server Error) if the fichierResultat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichier-resultats")
    public ResponseEntity<FichierResultat> updateFichierResultat(@Valid @RequestBody FichierResultat fichierResultat) throws URISyntaxException {
        log.debug("REST request to update FichierResultat : {}", fichierResultat);
        if (fichierResultat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichierResultat result = fichierResultatService.save(fichierResultat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichierResultat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichier-resultats : get all the fichierResultats.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of fichierResultats in body
     */
    @GetMapping("/fichier-resultats")
    public ResponseEntity<List<FichierResultat>> getAllFichierResultats(FichierResultatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FichierResultats by criteria: {}", criteria);
        Page<FichierResultat> page = fichierResultatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichier-resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /fichier-resultats/count : count all the fichierResultats.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/fichier-resultats/count")
    public ResponseEntity<Long> countFichierResultats(FichierResultatCriteria criteria) {
        log.debug("REST request to count FichierResultats by criteria: {}", criteria);
        return ResponseEntity.ok().body(fichierResultatQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /fichier-resultats/:id : get the "id" fichierResultat.
     *
     * @param id the id of the fichierResultat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichierResultat, or with status 404 (Not Found)
     */
    @GetMapping("/fichier-resultats/{id}")
    public ResponseEntity<FichierResultat> getFichierResultat(@PathVariable Long id) {
        log.debug("REST request to get FichierResultat : {}", id);
        Optional<FichierResultat> fichierResultat = fichierResultatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichierResultat);
    }

    /**
     * DELETE  /fichier-resultats/:id : delete the "id" fichierResultat.
     *
     * @param id the id of the fichierResultat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichier-resultats/{id}")
    public ResponseEntity<Void> deleteFichierResultat(@PathVariable Long id) {
        log.debug("REST request to delete FichierResultat : {}", id);
        fichierResultatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fichier-resultats?query=:query : search for the fichierResultat corresponding
     * to the query.
     *
     * @param query the query of the fichierResultat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fichier-resultats")
    public ResponseEntity<List<FichierResultat>> searchFichierResultats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FichierResultats for query {}", query);
        Page<FichierResultat> page = fichierResultatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fichier-resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
