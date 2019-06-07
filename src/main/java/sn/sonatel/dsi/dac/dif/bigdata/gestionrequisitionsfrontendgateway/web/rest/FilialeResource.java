package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FilialeService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FilialeCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.FilialeQueryService;
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
 * REST controller for managing Filiale.
 */
@RestController
@RequestMapping("/api")
public class FilialeResource {

    private final Logger log = LoggerFactory.getLogger(FilialeResource.class);

    private static final String ENTITY_NAME = "filiale";

    private final FilialeService filialeService;

    private final FilialeQueryService filialeQueryService;

    public FilialeResource(FilialeService filialeService, FilialeQueryService filialeQueryService) {
        this.filialeService = filialeService;
        this.filialeQueryService = filialeQueryService;
    }

    /**
     * POST  /filiales : Create a new filiale.
     *
     * @param filiale the filiale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filiale, or with status 400 (Bad Request) if the filiale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filiales")
    public ResponseEntity<Filiale> createFiliale(@Valid @RequestBody Filiale filiale) throws URISyntaxException {
        log.debug("REST request to save Filiale : {}", filiale);
        if (filiale.getId() != null) {
            throw new BadRequestAlertException("A new filiale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Filiale result = filialeService.save(filiale);
        return ResponseEntity.created(new URI("/api/filiales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filiales : Updates an existing filiale.
     *
     * @param filiale the filiale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filiale,
     * or with status 400 (Bad Request) if the filiale is not valid,
     * or with status 500 (Internal Server Error) if the filiale couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filiales")
    public ResponseEntity<Filiale> updateFiliale(@Valid @RequestBody Filiale filiale) throws URISyntaxException {
        log.debug("REST request to update Filiale : {}", filiale);
        if (filiale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Filiale result = filialeService.save(filiale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filiale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filiales : get all the filiales.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of filiales in body
     */
    @GetMapping("/filiales")
    public ResponseEntity<List<Filiale>> getAllFiliales(FilialeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Filiales by criteria: {}", criteria);
        Page<Filiale> page = filialeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filiales");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /filiales/count : count all the filiales.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/filiales/count")
    public ResponseEntity<Long> countFiliales(FilialeCriteria criteria) {
        log.debug("REST request to count Filiales by criteria: {}", criteria);
        return ResponseEntity.ok().body(filialeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /filiales/:id : get the "id" filiale.
     *
     * @param id the id of the filiale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filiale, or with status 404 (Not Found)
     */
    @GetMapping("/filiales/{id}")
    public ResponseEntity<Filiale> getFiliale(@PathVariable Long id) {
        log.debug("REST request to get Filiale : {}", id);
        Optional<Filiale> filiale = filialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filiale);
    }

    /**
     * DELETE  /filiales/:id : delete the "id" filiale.
     *
     * @param id the id of the filiale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filiales/{id}")
    public ResponseEntity<Void> deleteFiliale(@PathVariable Long id) {
        log.debug("REST request to delete Filiale : {}", id);
        filialeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/filiales?query=:query : search for the filiale corresponding
     * to the query.
     *
     * @param query the query of the filiale search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/filiales")
    public ResponseEntity<List<Filiale>> searchFiliales(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Filiales for query {}", query);
        Page<Filiale> page = filialeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/filiales");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
