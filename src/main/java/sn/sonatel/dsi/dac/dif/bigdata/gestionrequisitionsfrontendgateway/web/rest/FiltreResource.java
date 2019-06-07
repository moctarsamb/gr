package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FiltreService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FiltreCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FiltreQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Filtre.
 */
@RestController
@RequestMapping("/api")
public class FiltreResource {

    private final Logger log = LoggerFactory.getLogger(FiltreResource.class);

    private static final String ENTITY_NAME = "filtre";

    private final FiltreService filtreService;

    private final FiltreQueryService filtreQueryService;

    public FiltreResource(FiltreService filtreService, FiltreQueryService filtreQueryService) {
        this.filtreService = filtreService;
        this.filtreQueryService = filtreQueryService;
    }

    /**
     * POST  /filtres : Create a new filtre.
     *
     * @param filtre the filtre to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filtre, or with status 400 (Bad Request) if the filtre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filtres")
    public ResponseEntity<Filtre> createFiltre(@RequestBody Filtre filtre) throws URISyntaxException {
        log.debug("REST request to save Filtre : {}", filtre);
        if (filtre.getId() != null) {
            throw new BadRequestAlertException("A new filtre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Filtre result = filtreService.save(filtre);
        return ResponseEntity.created(new URI("/api/filtres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filtres : Updates an existing filtre.
     *
     * @param filtre the filtre to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filtre,
     * or with status 400 (Bad Request) if the filtre is not valid,
     * or with status 500 (Internal Server Error) if the filtre couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filtres")
    public ResponseEntity<Filtre> updateFiltre(@RequestBody Filtre filtre) throws URISyntaxException {
        log.debug("REST request to update Filtre : {}", filtre);
        if (filtre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Filtre result = filtreService.save(filtre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filtre.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filtres : get all the filtres.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of filtres in body
     */
    @GetMapping("/filtres")
    public ResponseEntity<List<Filtre>> getAllFiltres(FiltreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Filtres by criteria: {}", criteria);
        Page<Filtre> page = filtreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filtres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /filtres/count : count all the filtres.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/filtres/count")
    public ResponseEntity<Long> countFiltres(FiltreCriteria criteria) {
        log.debug("REST request to count Filtres by criteria: {}", criteria);
        return ResponseEntity.ok().body(filtreQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /filtres/:id : get the "id" filtre.
     *
     * @param id the id of the filtre to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filtre, or with status 404 (Not Found)
     */
    @GetMapping("/filtres/{id}")
    public ResponseEntity<Filtre> getFiltre(@PathVariable Long id) {
        log.debug("REST request to get Filtre : {}", id);
        Optional<Filtre> filtre = filtreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filtre);
    }

    /**
     * DELETE  /filtres/:id : delete the "id" filtre.
     *
     * @param id the id of the filtre to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filtres/{id}")
    public ResponseEntity<Void> deleteFiltre(@PathVariable Long id) {
        log.debug("REST request to delete Filtre : {}", id);
        filtreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/filtres?query=:query : search for the filtre corresponding
     * to the query.
     *
     * @param query the query of the filtre search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/filtres")
    public ResponseEntity<List<Filtre>> searchFiltres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Filtres for query {}", query);
        Page<Filtre> page = filtreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/filtres");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
