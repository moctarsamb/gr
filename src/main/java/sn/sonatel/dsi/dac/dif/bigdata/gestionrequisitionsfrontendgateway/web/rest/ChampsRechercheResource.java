package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ChampsRechercheService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ChampsRechercheCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ChampsRechercheQueryService;
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
 * REST controller for managing ChampsRecherche.
 */
@RestController
@RequestMapping("/api")
public class ChampsRechercheResource {

    private final Logger log = LoggerFactory.getLogger(ChampsRechercheResource.class);

    private static final String ENTITY_NAME = "champsRecherche";

    private final ChampsRechercheService champsRechercheService;

    private final ChampsRechercheQueryService champsRechercheQueryService;

    public ChampsRechercheResource(ChampsRechercheService champsRechercheService, ChampsRechercheQueryService champsRechercheQueryService) {
        this.champsRechercheService = champsRechercheService;
        this.champsRechercheQueryService = champsRechercheQueryService;
    }

    /**
     * POST  /champs-recherches : Create a new champsRecherche.
     *
     * @param champsRecherche the champsRecherche to create
     * @return the ResponseEntity with status 201 (Created) and with body the new champsRecherche, or with status 400 (Bad Request) if the champsRecherche has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/champs-recherches")
    public ResponseEntity<ChampsRecherche> createChampsRecherche(@Valid @RequestBody ChampsRecherche champsRecherche) throws URISyntaxException {
        log.debug("REST request to save ChampsRecherche : {}", champsRecherche);
        if (champsRecherche.getId() != null) {
            throw new BadRequestAlertException("A new champsRecherche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChampsRecherche result = champsRechercheService.save(champsRecherche);
        return ResponseEntity.created(new URI("/api/champs-recherches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /champs-recherches : Updates an existing champsRecherche.
     *
     * @param champsRecherche the champsRecherche to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated champsRecherche,
     * or with status 400 (Bad Request) if the champsRecherche is not valid,
     * or with status 500 (Internal Server Error) if the champsRecherche couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/champs-recherches")
    public ResponseEntity<ChampsRecherche> updateChampsRecherche(@Valid @RequestBody ChampsRecherche champsRecherche) throws URISyntaxException {
        log.debug("REST request to update ChampsRecherche : {}", champsRecherche);
        if (champsRecherche.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChampsRecherche result = champsRechercheService.save(champsRecherche);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, champsRecherche.getId().toString()))
            .body(result);
    }

    /**
     * GET  /champs-recherches : get all the champsRecherches.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of champsRecherches in body
     */
    @GetMapping("/champs-recherches")
    public ResponseEntity<List<ChampsRecherche>> getAllChampsRecherches(ChampsRechercheCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ChampsRecherches by criteria: {}", criteria);
        Page<ChampsRecherche> page = champsRechercheQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/champs-recherches");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /champs-recherches/count : count all the champsRecherches.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/champs-recherches/count")
    public ResponseEntity<Long> countChampsRecherches(ChampsRechercheCriteria criteria) {
        log.debug("REST request to count ChampsRecherches by criteria: {}", criteria);
        return ResponseEntity.ok().body(champsRechercheQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /champs-recherches/:id : get the "id" champsRecherche.
     *
     * @param id the id of the champsRecherche to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the champsRecherche, or with status 404 (Not Found)
     */
    @GetMapping("/champs-recherches/{id}")
    public ResponseEntity<ChampsRecherche> getChampsRecherche(@PathVariable Long id) {
        log.debug("REST request to get ChampsRecherche : {}", id);
        Optional<ChampsRecherche> champsRecherche = champsRechercheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(champsRecherche);
    }

    /**
     * DELETE  /champs-recherches/:id : delete the "id" champsRecherche.
     *
     * @param id the id of the champsRecherche to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/champs-recherches/{id}")
    public ResponseEntity<Void> deleteChampsRecherche(@PathVariable Long id) {
        log.debug("REST request to delete ChampsRecherche : {}", id);
        champsRechercheService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/champs-recherches?query=:query : search for the champsRecherche corresponding
     * to the query.
     *
     * @param query the query of the champsRecherche search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/champs-recherches")
    public ResponseEntity<List<ChampsRecherche>> searchChampsRecherches(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChampsRecherches for query {}", query);
        Page<ChampsRecherche> page = champsRechercheService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/champs-recherches");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
