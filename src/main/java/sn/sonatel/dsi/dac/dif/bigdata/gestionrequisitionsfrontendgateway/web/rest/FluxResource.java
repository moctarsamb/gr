package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FluxService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FluxCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FluxQueryService;
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
 * REST controller for managing Flux.
 */
@RestController
@RequestMapping("/api")
public class FluxResource {

    private final Logger log = LoggerFactory.getLogger(FluxResource.class);

    private static final String ENTITY_NAME = "flux";

    private final FluxService fluxService;

    private final FluxQueryService fluxQueryService;

    public FluxResource(FluxService fluxService, FluxQueryService fluxQueryService) {
        this.fluxService = fluxService;
        this.fluxQueryService = fluxQueryService;
    }

    /**
     * POST  /fluxes : Create a new flux.
     *
     * @param flux the flux to create
     * @return the ResponseEntity with status 201 (Created) and with body the new flux, or with status 400 (Bad Request) if the flux has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fluxes")
    public ResponseEntity<Flux> createFlux(@Valid @RequestBody Flux flux) throws URISyntaxException {
        log.debug("REST request to save Flux : {}", flux);
        if (flux.getId() != null) {
            throw new BadRequestAlertException("A new flux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Flux result = fluxService.save(flux);
        return ResponseEntity.created(new URI("/api/fluxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fluxes : Updates an existing flux.
     *
     * @param flux the flux to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated flux,
     * or with status 400 (Bad Request) if the flux is not valid,
     * or with status 500 (Internal Server Error) if the flux couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fluxes")
    public ResponseEntity<Flux> updateFlux(@Valid @RequestBody Flux flux) throws URISyntaxException {
        log.debug("REST request to update Flux : {}", flux);
        if (flux.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Flux result = fluxService.save(flux);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, flux.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fluxes : get all the fluxes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of fluxes in body
     */
    @GetMapping("/fluxes")
    public ResponseEntity<List<Flux>> getAllFluxes(FluxCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fluxes by criteria: {}", criteria);
        Page<Flux> page = fluxQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fluxes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /fluxes/count : count all the fluxes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/fluxes/count")
    public ResponseEntity<Long> countFluxes(FluxCriteria criteria) {
        log.debug("REST request to count Fluxes by criteria: {}", criteria);
        return ResponseEntity.ok().body(fluxQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /fluxes/:id : get the "id" flux.
     *
     * @param id the id of the flux to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the flux, or with status 404 (Not Found)
     */
    @GetMapping("/fluxes/{id}")
    public ResponseEntity<Flux> getFlux(@PathVariable Long id) {
        log.debug("REST request to get Flux : {}", id);
        Optional<Flux> flux = fluxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flux);
    }

    /**
     * DELETE  /fluxes/:id : delete the "id" flux.
     *
     * @param id the id of the flux to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fluxes/{id}")
    public ResponseEntity<Void> deleteFlux(@PathVariable Long id) {
        log.debug("REST request to delete Flux : {}", id);
        fluxService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fluxes?query=:query : search for the flux corresponding
     * to the query.
     *
     * @param query the query of the flux search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fluxes")
    public ResponseEntity<List<Flux>> searchFluxes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fluxes for query {}", query);
        Page<Flux> page = fluxService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fluxes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
