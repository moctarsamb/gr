package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvoiResultatService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.EnvoiResultatCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.EnvoiResultatQueryService;
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
 * REST controller for managing EnvoiResultat.
 */
@RestController
@RequestMapping("/api")
public class EnvoiResultatResource {

    private final Logger log = LoggerFactory.getLogger(EnvoiResultatResource.class);

    private static final String ENTITY_NAME = "envoiResultat";

    private final EnvoiResultatService envoiResultatService;

    private final EnvoiResultatQueryService envoiResultatQueryService;

    public EnvoiResultatResource(EnvoiResultatService envoiResultatService, EnvoiResultatQueryService envoiResultatQueryService) {
        this.envoiResultatService = envoiResultatService;
        this.envoiResultatQueryService = envoiResultatQueryService;
    }

    /**
     * POST  /envoi-resultats : Create a new envoiResultat.
     *
     * @param envoiResultat the envoiResultat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new envoiResultat, or with status 400 (Bad Request) if the envoiResultat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/envoi-resultats")
    public ResponseEntity<EnvoiResultat> createEnvoiResultat(@Valid @RequestBody EnvoiResultat envoiResultat) throws URISyntaxException {
        log.debug("REST request to save EnvoiResultat : {}", envoiResultat);
        if (envoiResultat.getId() != null) {
            throw new BadRequestAlertException("A new envoiResultat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvoiResultat result = envoiResultatService.save(envoiResultat);
        return ResponseEntity.created(new URI("/api/envoi-resultats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /envoi-resultats : Updates an existing envoiResultat.
     *
     * @param envoiResultat the envoiResultat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated envoiResultat,
     * or with status 400 (Bad Request) if the envoiResultat is not valid,
     * or with status 500 (Internal Server Error) if the envoiResultat couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/envoi-resultats")
    public ResponseEntity<EnvoiResultat> updateEnvoiResultat(@Valid @RequestBody EnvoiResultat envoiResultat) throws URISyntaxException {
        log.debug("REST request to update EnvoiResultat : {}", envoiResultat);
        if (envoiResultat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnvoiResultat result = envoiResultatService.save(envoiResultat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, envoiResultat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /envoi-resultats : get all the envoiResultats.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of envoiResultats in body
     */
    @GetMapping("/envoi-resultats")
    public ResponseEntity<List<EnvoiResultat>> getAllEnvoiResultats(EnvoiResultatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EnvoiResultats by criteria: {}", criteria);
        Page<EnvoiResultat> page = envoiResultatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/envoi-resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /envoi-resultats/count : count all the envoiResultats.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/envoi-resultats/count")
    public ResponseEntity<Long> countEnvoiResultats(EnvoiResultatCriteria criteria) {
        log.debug("REST request to count EnvoiResultats by criteria: {}", criteria);
        return ResponseEntity.ok().body(envoiResultatQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /envoi-resultats/:id : get the "id" envoiResultat.
     *
     * @param id the id of the envoiResultat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the envoiResultat, or with status 404 (Not Found)
     */
    @GetMapping("/envoi-resultats/{id}")
    public ResponseEntity<EnvoiResultat> getEnvoiResultat(@PathVariable Long id) {
        log.debug("REST request to get EnvoiResultat : {}", id);
        Optional<EnvoiResultat> envoiResultat = envoiResultatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(envoiResultat);
    }

    /**
     * DELETE  /envoi-resultats/:id : delete the "id" envoiResultat.
     *
     * @param id the id of the envoiResultat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/envoi-resultats/{id}")
    public ResponseEntity<Void> deleteEnvoiResultat(@PathVariable Long id) {
        log.debug("REST request to delete EnvoiResultat : {}", id);
        envoiResultatService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/envoi-resultats?query=:query : search for the envoiResultat corresponding
     * to the query.
     *
     * @param query the query of the envoiResultat search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/envoi-resultats")
    public ResponseEntity<List<EnvoiResultat>> searchEnvoiResultats(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EnvoiResultats for query {}", query);
        Page<EnvoiResultat> page = envoiResultatService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/envoi-resultats");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
