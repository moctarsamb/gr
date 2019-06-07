package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.BaseService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.BaseCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.BaseQueryService;
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
 * REST controller for managing Base.
 */
@RestController
@RequestMapping("/api")
public class BaseResource {

    private final Logger log = LoggerFactory.getLogger(BaseResource.class);

    private static final String ENTITY_NAME = "base";

    private final BaseService baseService;

    private final BaseQueryService baseQueryService;

    public BaseResource(BaseService baseService, BaseQueryService baseQueryService) {
        this.baseService = baseService;
        this.baseQueryService = baseQueryService;
    }

    /**
     * POST  /bases : Create a new base.
     *
     * @param base the base to create
     * @return the ResponseEntity with status 201 (Created) and with body the new base, or with status 400 (Bad Request) if the base has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bases")
    public ResponseEntity<Base> createBase(@Valid @RequestBody Base base) throws URISyntaxException {
        log.debug("REST request to save Base : {}", base);
        if (base.getId() != null) {
            throw new BadRequestAlertException("A new base cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Base result = baseService.save(base);
        return ResponseEntity.created(new URI("/api/bases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bases : Updates an existing base.
     *
     * @param base the base to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated base,
     * or with status 400 (Bad Request) if the base is not valid,
     * or with status 500 (Internal Server Error) if the base couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bases")
    public ResponseEntity<Base> updateBase(@Valid @RequestBody Base base) throws URISyntaxException {
        log.debug("REST request to update Base : {}", base);
        if (base.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Base result = baseService.save(base);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, base.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bases : get all the bases.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bases in body
     */
    @GetMapping("/bases")
    public ResponseEntity<List<Base>> getAllBases(BaseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bases by criteria: {}", criteria);
        Page<Base> page = baseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /bases/count : count all the bases.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/bases/count")
    public ResponseEntity<Long> countBases(BaseCriteria criteria) {
        log.debug("REST request to count Bases by criteria: {}", criteria);
        return ResponseEntity.ok().body(baseQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /bases/:id : get the "id" base.
     *
     * @param id the id of the base to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the base, or with status 404 (Not Found)
     */
    @GetMapping("/bases/{id}")
    public ResponseEntity<Base> getBase(@PathVariable Long id) {
        log.debug("REST request to get Base : {}", id);
        Optional<Base> base = baseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(base);
    }

    /**
     * DELETE  /bases/:id : delete the "id" base.
     *
     * @param id the id of the base to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bases/{id}")
    public ResponseEntity<Void> deleteBase(@PathVariable Long id) {
        log.debug("REST request to delete Base : {}", id);
        baseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bases?query=:query : search for the base corresponding
     * to the query.
     *
     * @param query the query of the base search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bases")
    public ResponseEntity<List<Base>> searchBases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Bases for query {}", query);
        Page<Base> page = baseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
