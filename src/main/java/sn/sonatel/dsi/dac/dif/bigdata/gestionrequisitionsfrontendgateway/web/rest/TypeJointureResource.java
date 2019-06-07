package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeJointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeJointureService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypeJointureCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeJointureQueryService;
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
 * REST controller for managing TypeJointure.
 */
@RestController
@RequestMapping("/api")
public class TypeJointureResource {

    private final Logger log = LoggerFactory.getLogger(TypeJointureResource.class);

    private static final String ENTITY_NAME = "typeJointure";

    private final TypeJointureService typeJointureService;

    private final TypeJointureQueryService typeJointureQueryService;

    public TypeJointureResource(TypeJointureService typeJointureService, TypeJointureQueryService typeJointureQueryService) {
        this.typeJointureService = typeJointureService;
        this.typeJointureQueryService = typeJointureQueryService;
    }

    /**
     * POST  /type-jointures : Create a new typeJointure.
     *
     * @param typeJointure the typeJointure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeJointure, or with status 400 (Bad Request) if the typeJointure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-jointures")
    public ResponseEntity<TypeJointure> createTypeJointure(@Valid @RequestBody TypeJointure typeJointure) throws URISyntaxException {
        log.debug("REST request to save TypeJointure : {}", typeJointure);
        if (typeJointure.getId() != null) {
            throw new BadRequestAlertException("A new typeJointure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeJointure result = typeJointureService.save(typeJointure);
        return ResponseEntity.created(new URI("/api/type-jointures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-jointures : Updates an existing typeJointure.
     *
     * @param typeJointure the typeJointure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeJointure,
     * or with status 400 (Bad Request) if the typeJointure is not valid,
     * or with status 500 (Internal Server Error) if the typeJointure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-jointures")
    public ResponseEntity<TypeJointure> updateTypeJointure(@Valid @RequestBody TypeJointure typeJointure) throws URISyntaxException {
        log.debug("REST request to update TypeJointure : {}", typeJointure);
        if (typeJointure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeJointure result = typeJointureService.save(typeJointure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeJointure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-jointures : get all the typeJointures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typeJointures in body
     */
    @GetMapping("/type-jointures")
    public ResponseEntity<List<TypeJointure>> getAllTypeJointures(TypeJointureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeJointures by criteria: {}", criteria);
        Page<TypeJointure> page = typeJointureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-jointures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /type-jointures/count : count all the typeJointures.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/type-jointures/count")
    public ResponseEntity<Long> countTypeJointures(TypeJointureCriteria criteria) {
        log.debug("REST request to count TypeJointures by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeJointureQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /type-jointures/:id : get the "id" typeJointure.
     *
     * @param id the id of the typeJointure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeJointure, or with status 404 (Not Found)
     */
    @GetMapping("/type-jointures/{id}")
    public ResponseEntity<TypeJointure> getTypeJointure(@PathVariable Long id) {
        log.debug("REST request to get TypeJointure : {}", id);
        Optional<TypeJointure> typeJointure = typeJointureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeJointure);
    }

    /**
     * DELETE  /type-jointures/:id : delete the "id" typeJointure.
     *
     * @param id the id of the typeJointure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-jointures/{id}")
    public ResponseEntity<Void> deleteTypeJointure(@PathVariable Long id) {
        log.debug("REST request to delete TypeJointure : {}", id);
        typeJointureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-jointures?query=:query : search for the typeJointure corresponding
     * to the query.
     *
     * @param query the query of the typeJointure search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/type-jointures")
    public ResponseEntity<List<TypeJointure>> searchTypeJointures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TypeJointures for query {}", query);
        Page<TypeJointure> page = typeJointureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/type-jointures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
