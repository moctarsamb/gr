package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProfilService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ProfilCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ProfilQueryService;
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
 * REST controller for managing Profil.
 */
@RestController
@RequestMapping("/api")
public class ProfilResource {

    private final Logger log = LoggerFactory.getLogger(ProfilResource.class);

    private static final String ENTITY_NAME = "profil";

    private final ProfilService profilService;

    private final ProfilQueryService profilQueryService;

    public ProfilResource(ProfilService profilService, ProfilQueryService profilQueryService) {
        this.profilService = profilService;
        this.profilQueryService = profilQueryService;
    }

    /**
     * POST  /profils : Create a new profil.
     *
     * @param profil the profil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profil, or with status 400 (Bad Request) if the profil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profils")
    public ResponseEntity<Profil> createProfil(@Valid @RequestBody Profil profil) throws URISyntaxException {
        log.debug("REST request to save Profil : {}", profil);
        if (profil.getId() != null) {
            throw new BadRequestAlertException("A new profil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profil result = profilService.save(profil);
        return ResponseEntity.created(new URI("/api/profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profils : Updates an existing profil.
     *
     * @param profil the profil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profil,
     * or with status 400 (Bad Request) if the profil is not valid,
     * or with status 500 (Internal Server Error) if the profil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profils")
    public ResponseEntity<Profil> updateProfil(@Valid @RequestBody Profil profil) throws URISyntaxException {
        log.debug("REST request to update Profil : {}", profil);
        if (profil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Profil result = profilService.save(profil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profils : get all the profils.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of profils in body
     */
    @GetMapping("/profils")
    public ResponseEntity<List<Profil>> getAllProfils(ProfilCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Profils by criteria: {}", criteria);
        Page<Profil> page = profilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profils");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /profils/count : count all the profils.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/profils/count")
    public ResponseEntity<Long> countProfils(ProfilCriteria criteria) {
        log.debug("REST request to count Profils by criteria: {}", criteria);
        return ResponseEntity.ok().body(profilQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /profils/:id : get the "id" profil.
     *
     * @param id the id of the profil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profil, or with status 404 (Not Found)
     */
    @GetMapping("/profils/{id}")
    public ResponseEntity<Profil> getProfil(@PathVariable Long id) {
        log.debug("REST request to get Profil : {}", id);
        Optional<Profil> profil = profilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profil);
    }

    /**
     * DELETE  /profils/:id : delete the "id" profil.
     *
     * @param id the id of the profil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profils/{id}")
    public ResponseEntity<Void> deleteProfil(@PathVariable Long id) {
        log.debug("REST request to delete Profil : {}", id);
        profilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/profils?query=:query : search for the profil corresponding
     * to the query.
     *
     * @param query the query of the profil search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/profils")
    public ResponseEntity<List<Profil>> searchProfils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Profils for query {}", query);
        Page<Profil> page = profilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/profils");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
