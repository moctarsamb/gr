package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.JointureService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.JointureCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.JointureQueryService;
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
 * REST controller for managing Jointure.
 */
@RestController
@RequestMapping("/api")
public class JointureResource {

    private final Logger log = LoggerFactory.getLogger(JointureResource.class);

    private static final String ENTITY_NAME = "jointure";

    private final JointureService jointureService;

    private final JointureQueryService jointureQueryService;

    public JointureResource(JointureService jointureService, JointureQueryService jointureQueryService) {
        this.jointureService = jointureService;
        this.jointureQueryService = jointureQueryService;
    }

    /**
     * POST  /jointures : Create a new jointure.
     *
     * @param jointure the jointure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jointure, or with status 400 (Bad Request) if the jointure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jointures")
    public ResponseEntity<Jointure> createJointure(@RequestBody Jointure jointure) throws URISyntaxException {
        log.debug("REST request to save Jointure : {}", jointure);
        if (jointure.getId() != null) {
            throw new BadRequestAlertException("A new jointure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jointure result = jointureService.save(jointure);
        return ResponseEntity.created(new URI("/api/jointures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jointures : Updates an existing jointure.
     *
     * @param jointure the jointure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jointure,
     * or with status 400 (Bad Request) if the jointure is not valid,
     * or with status 500 (Internal Server Error) if the jointure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jointures")
    public ResponseEntity<Jointure> updateJointure(@RequestBody Jointure jointure) throws URISyntaxException {
        log.debug("REST request to update Jointure : {}", jointure);
        if (jointure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Jointure result = jointureService.save(jointure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jointure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jointures : get all the jointures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of jointures in body
     */
    @GetMapping("/jointures")
    public ResponseEntity<List<Jointure>> getAllJointures(JointureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Jointures by criteria: {}", criteria);
        Page<Jointure> page = jointureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jointures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /jointures/count : count all the jointures.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/jointures/count")
    public ResponseEntity<Long> countJointures(JointureCriteria criteria) {
        log.debug("REST request to count Jointures by criteria: {}", criteria);
        return ResponseEntity.ok().body(jointureQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /jointures/:id : get the "id" jointure.
     *
     * @param id the id of the jointure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jointure, or with status 404 (Not Found)
     */
    @GetMapping("/jointures/{id}")
    public ResponseEntity<Jointure> getJointure(@PathVariable Long id) {
        log.debug("REST request to get Jointure : {}", id);
        Optional<Jointure> jointure = jointureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jointure);
    }

    /**
     * DELETE  /jointures/:id : delete the "id" jointure.
     *
     * @param id the id of the jointure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jointures/{id}")
    public ResponseEntity<Void> deleteJointure(@PathVariable Long id) {
        log.debug("REST request to delete Jointure : {}", id);
        jointureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/jointures?query=:query : search for the jointure corresponding
     * to the query.
     *
     * @param query the query of the jointure search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/jointures")
    public ResponseEntity<List<Jointure>> searchJointures(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Jointures for query {}", query);
        Page<Jointure> page = jointureService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/jointures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
