package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeExtractionService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypeExtractionCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.TypeExtractionQueryService;
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
 * REST controller for managing TypeExtraction.
 */
@RestController
@RequestMapping("/api")
public class TypeExtractionResource {

    private final Logger log = LoggerFactory.getLogger(TypeExtractionResource.class);

    private static final String ENTITY_NAME = "typeExtraction";

    private final TypeExtractionService typeExtractionService;

    private final TypeExtractionQueryService typeExtractionQueryService;

    public TypeExtractionResource(TypeExtractionService typeExtractionService, TypeExtractionQueryService typeExtractionQueryService) {
        this.typeExtractionService = typeExtractionService;
        this.typeExtractionQueryService = typeExtractionQueryService;
    }

    /**
     * POST  /type-extractions : Create a new typeExtraction.
     *
     * @param typeExtraction the typeExtraction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeExtraction, or with status 400 (Bad Request) if the typeExtraction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-extractions")
    public ResponseEntity<TypeExtraction> createTypeExtraction(@Valid @RequestBody TypeExtraction typeExtraction) throws URISyntaxException {
        log.debug("REST request to save TypeExtraction : {}", typeExtraction);
        if (typeExtraction.getId() != null) {
            throw new BadRequestAlertException("A new typeExtraction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeExtraction result = typeExtractionService.save(typeExtraction);
        return ResponseEntity.created(new URI("/api/type-extractions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-extractions : Updates an existing typeExtraction.
     *
     * @param typeExtraction the typeExtraction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeExtraction,
     * or with status 400 (Bad Request) if the typeExtraction is not valid,
     * or with status 500 (Internal Server Error) if the typeExtraction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-extractions")
    public ResponseEntity<TypeExtraction> updateTypeExtraction(@Valid @RequestBody TypeExtraction typeExtraction) throws URISyntaxException {
        log.debug("REST request to update TypeExtraction : {}", typeExtraction);
        if (typeExtraction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeExtraction result = typeExtractionService.save(typeExtraction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeExtraction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-extractions : get all the typeExtractions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typeExtractions in body
     */
    @GetMapping("/type-extractions")
    public ResponseEntity<List<TypeExtraction>> getAllTypeExtractions(TypeExtractionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeExtractions by criteria: {}", criteria);
        Page<TypeExtraction> page = typeExtractionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-extractions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /type-extractions/count : count all the typeExtractions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/type-extractions/count")
    public ResponseEntity<Long> countTypeExtractions(TypeExtractionCriteria criteria) {
        log.debug("REST request to count TypeExtractions by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeExtractionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /type-extractions/:id : get the "id" typeExtraction.
     *
     * @param id the id of the typeExtraction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeExtraction, or with status 404 (Not Found)
     */
    @GetMapping("/type-extractions/{id}")
    public ResponseEntity<TypeExtraction> getTypeExtraction(@PathVariable Long id) {
        log.debug("REST request to get TypeExtraction : {}", id);
        Optional<TypeExtraction> typeExtraction = typeExtractionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeExtraction);
    }

    /**
     * DELETE  /type-extractions/:id : delete the "id" typeExtraction.
     *
     * @param id the id of the typeExtraction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-extractions/{id}")
    public ResponseEntity<Void> deleteTypeExtraction(@PathVariable Long id) {
        log.debug("REST request to delete TypeExtraction : {}", id);
        typeExtractionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-extractions?query=:query : search for the typeExtraction corresponding
     * to the query.
     *
     * @param query the query of the typeExtraction search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/type-extractions")
    public ResponseEntity<List<TypeExtraction>> searchTypeExtractions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TypeExtractions for query {}", query);
        Page<TypeExtraction> page = typeExtractionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/type-extractions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
