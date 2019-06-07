package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Serveur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ServeurService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ServeurCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ServeurQueryService;
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
 * REST controller for managing Serveur.
 */
@RestController
@RequestMapping("/api")
public class ServeurResource {

    private final Logger log = LoggerFactory.getLogger(ServeurResource.class);

    private static final String ENTITY_NAME = "serveur";

    private final ServeurService serveurService;

    private final ServeurQueryService serveurQueryService;

    public ServeurResource(ServeurService serveurService, ServeurQueryService serveurQueryService) {
        this.serveurService = serveurService;
        this.serveurQueryService = serveurQueryService;
    }

    /**
     * POST  /serveurs : Create a new serveur.
     *
     * @param serveur the serveur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serveur, or with status 400 (Bad Request) if the serveur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/serveurs")
    public ResponseEntity<Serveur> createServeur(@Valid @RequestBody Serveur serveur) throws URISyntaxException {
        log.debug("REST request to save Serveur : {}", serveur);
        if (serveur.getId() != null) {
            throw new BadRequestAlertException("A new serveur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Serveur result = serveurService.save(serveur);
        return ResponseEntity.created(new URI("/api/serveurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /serveurs : Updates an existing serveur.
     *
     * @param serveur the serveur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serveur,
     * or with status 400 (Bad Request) if the serveur is not valid,
     * or with status 500 (Internal Server Error) if the serveur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/serveurs")
    public ResponseEntity<Serveur> updateServeur(@Valid @RequestBody Serveur serveur) throws URISyntaxException {
        log.debug("REST request to update Serveur : {}", serveur);
        if (serveur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Serveur result = serveurService.save(serveur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serveur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /serveurs : get all the serveurs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of serveurs in body
     */
    @GetMapping("/serveurs")
    public ResponseEntity<List<Serveur>> getAllServeurs(ServeurCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Serveurs by criteria: {}", criteria);
        Page<Serveur> page = serveurQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/serveurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /serveurs/count : count all the serveurs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/serveurs/count")
    public ResponseEntity<Long> countServeurs(ServeurCriteria criteria) {
        log.debug("REST request to count Serveurs by criteria: {}", criteria);
        return ResponseEntity.ok().body(serveurQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /serveurs/:id : get the "id" serveur.
     *
     * @param id the id of the serveur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serveur, or with status 404 (Not Found)
     */
    @GetMapping("/serveurs/{id}")
    public ResponseEntity<Serveur> getServeur(@PathVariable Long id) {
        log.debug("REST request to get Serveur : {}", id);
        Optional<Serveur> serveur = serveurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serveur);
    }

    /**
     * DELETE  /serveurs/:id : delete the "id" serveur.
     *
     * @param id the id of the serveur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/serveurs/{id}")
    public ResponseEntity<Void> deleteServeur(@PathVariable Long id) {
        log.debug("REST request to delete Serveur : {}", id);
        serveurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/serveurs?query=:query : search for the serveur corresponding
     * to the query.
     *
     * @param query the query of the serveur search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/serveurs")
    public ResponseEntity<List<Serveur>> searchServeurs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Serveurs for query {}", query);
        Page<Serveur> page = serveurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/serveurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
