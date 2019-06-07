package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DimensionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.DimensionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.DimensionQueryService;
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
 * REST controller for managing Dimension.
 */
@RestController
@RequestMapping("/api")
public class DimensionResource {

    private final Logger log = LoggerFactory.getLogger(DimensionResource.class);

    private static final String ENTITY_NAME = "dimension";

    private final DimensionService dimensionService;

    private final DimensionQueryService dimensionQueryService;

    public DimensionResource(DimensionService dimensionService, DimensionQueryService dimensionQueryService) {
        this.dimensionService = dimensionService;
        this.dimensionQueryService = dimensionQueryService;
    }

    /**
     * POST  /dimensions : Create a new dimension.
     *
     * @param dimension the dimension to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dimension, or with status 400 (Bad Request) if the dimension has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dimensions")
    public ResponseEntity<Dimension> createDimension(@RequestBody Dimension dimension) throws URISyntaxException {
        log.debug("REST request to save Dimension : {}", dimension);
        if (dimension.getId() != null) {
            throw new BadRequestAlertException("A new dimension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dimension result = dimensionService.save(dimension);
        return ResponseEntity.created(new URI("/api/dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dimensions : Updates an existing dimension.
     *
     * @param dimension the dimension to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dimension,
     * or with status 400 (Bad Request) if the dimension is not valid,
     * or with status 500 (Internal Server Error) if the dimension couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dimensions")
    public ResponseEntity<Dimension> updateDimension(@RequestBody Dimension dimension) throws URISyntaxException {
        log.debug("REST request to update Dimension : {}", dimension);
        if (dimension.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dimension result = dimensionService.save(dimension);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dimension.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dimensions : get all the dimensions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dimensions in body
     */
    @GetMapping("/dimensions")
    public ResponseEntity<List<Dimension>> getAllDimensions(DimensionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Dimensions by criteria: {}", criteria);
        Page<Dimension> page = dimensionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dimensions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /dimensions/count : count all the dimensions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/dimensions/count")
    public ResponseEntity<Long> countDimensions(DimensionCriteria criteria) {
        log.debug("REST request to count Dimensions by criteria: {}", criteria);
        return ResponseEntity.ok().body(dimensionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /dimensions/:id : get the "id" dimension.
     *
     * @param id the id of the dimension to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dimension, or with status 404 (Not Found)
     */
    @GetMapping("/dimensions/{id}")
    public ResponseEntity<Dimension> getDimension(@PathVariable Long id) {
        log.debug("REST request to get Dimension : {}", id);
        Optional<Dimension> dimension = dimensionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dimension);
    }

    /**
     * DELETE  /dimensions/:id : delete the "id" dimension.
     *
     * @param id the id of the dimension to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dimensions/{id}")
    public ResponseEntity<Void> deleteDimension(@PathVariable Long id) {
        log.debug("REST request to delete Dimension : {}", id);
        dimensionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dimensions?query=:query : search for the dimension corresponding
     * to the query.
     *
     * @param query the query of the dimension search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/dimensions")
    public ResponseEntity<List<Dimension>> searchDimensions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dimensions for query {}", query);
        Page<Dimension> page = dimensionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/dimensions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
