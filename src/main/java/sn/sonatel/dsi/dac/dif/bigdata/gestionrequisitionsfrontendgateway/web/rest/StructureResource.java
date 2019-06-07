package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.StructureService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.StructureCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.StructureQueryService;
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
 * REST controller for managing Structure.
 */
@RestController
@RequestMapping("/api")
public class StructureResource {

    private final Logger log = LoggerFactory.getLogger(StructureResource.class);

    private static final String ENTITY_NAME = "structure";

    private final StructureService structureService;

    private final StructureQueryService structureQueryService;

    public StructureResource(StructureService structureService, StructureQueryService structureQueryService) {
        this.structureService = structureService;
        this.structureQueryService = structureQueryService;
    }

    /**
     * POST  /structures : Create a new structure.
     *
     * @param structure the structure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new structure, or with status 400 (Bad Request) if the structure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/structures")
    public ResponseEntity<Structure> createStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.debug("REST request to save Structure : {}", structure);
        if (structure.getId() != null) {
            throw new BadRequestAlertException("A new structure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Structure result = structureService.save(structure);
        return ResponseEntity.created(new URI("/api/structures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /structures : Updates an existing structure.
     *
     * @param structure the structure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated structure,
     * or with status 400 (Bad Request) if the structure is not valid,
     * or with status 500 (Internal Server Error) if the structure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/structures")
    public ResponseEntity<Structure> updateStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        log.debug("REST request to update Structure : {}", structure);
        if (structure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Structure result = structureService.save(structure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, structure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /structures : get all the structures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of structures in body
     */
    @GetMapping("/structures")
    public ResponseEntity<List<Structure>> getAllStructures(StructureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Structures by criteria: {}", criteria);
        Page<Structure> page = structureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/structures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /structures/count : count all the structures.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/structures/count")
    public ResponseEntity<Long> countStructures(StructureCriteria criteria) {
        log.debug("REST request to count Structures by criteria: {}", criteria);
        return ResponseEntity.ok().body(structureQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /structures/:id : get the "id" structure.
     *
     * @param id the id of the structure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the structure, or with status 404 (Not Found)
     */
    @GetMapping("/structures/{id}")
    public ResponseEntity<Structure> getStructure(@PathVariable Long id) {
        log.debug("REST request to get Structure : {}", id);
        Optional<Structure> structure = structureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(structure);
    }

    /**
     * DELETE  /structures/:id : delete the "id" structure.
     *
     * @param id the id of the structure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/structures/{id}")
    public ResponseEntity<Void> deleteStructure(@PathVariable Long id) {
        log.debug("REST request to delete Structure : {}", id);
        structureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/structures?query=:query : search for the structure corresponding
     * to the query.
     *
     * @param query the query of the structure search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/structures")
    public ResponseEntity<List<Structure>> searchStructures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Structures for query {}", query);
        Page<Structure> page = structureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/structures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
