package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ParametrageApplication;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ParametrageApplicationService;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.errors.BadRequestAlertException;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.HeaderUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.web.rest.util.PaginationUtil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ParametrageApplicationCriteria;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.ParametrageApplicationQueryService;
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
 * REST controller for managing ParametrageApplication.
 */
@RestController
@RequestMapping("/api")
public class ParametrageApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ParametrageApplicationResource.class);

    private static final String ENTITY_NAME = "parametrageApplication";

    private final ParametrageApplicationService parametrageApplicationService;

    private final ParametrageApplicationQueryService parametrageApplicationQueryService;

    public ParametrageApplicationResource(ParametrageApplicationService parametrageApplicationService, ParametrageApplicationQueryService parametrageApplicationQueryService) {
        this.parametrageApplicationService = parametrageApplicationService;
        this.parametrageApplicationQueryService = parametrageApplicationQueryService;
    }

    /**
     * POST  /parametrage-applications : Create a new parametrageApplication.
     *
     * @param parametrageApplication the parametrageApplication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametrageApplication, or with status 400 (Bad Request) if the parametrageApplication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametrage-applications")
    public ResponseEntity<ParametrageApplication> createParametrageApplication(@Valid @RequestBody ParametrageApplication parametrageApplication) throws URISyntaxException {
        log.debug("REST request to save ParametrageApplication : {}", parametrageApplication);
        if (parametrageApplication.getId() != null) {
            throw new BadRequestAlertException("A new parametrageApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametrageApplication result = parametrageApplicationService.save(parametrageApplication);
        return ResponseEntity.created(new URI("/api/parametrage-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametrage-applications : Updates an existing parametrageApplication.
     *
     * @param parametrageApplication the parametrageApplication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametrageApplication,
     * or with status 400 (Bad Request) if the parametrageApplication is not valid,
     * or with status 500 (Internal Server Error) if the parametrageApplication couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametrage-applications")
    public ResponseEntity<ParametrageApplication> updateParametrageApplication(@Valid @RequestBody ParametrageApplication parametrageApplication) throws URISyntaxException {
        log.debug("REST request to update ParametrageApplication : {}", parametrageApplication);
        if (parametrageApplication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametrageApplication result = parametrageApplicationService.save(parametrageApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametrageApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametrage-applications : get all the parametrageApplications.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of parametrageApplications in body
     */
    @GetMapping("/parametrage-applications")
    public ResponseEntity<List<ParametrageApplication>> getAllParametrageApplications(ParametrageApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ParametrageApplications by criteria: {}", criteria);
        Page<ParametrageApplication> page = parametrageApplicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametrage-applications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /parametrage-applications/count : count all the parametrageApplications.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/parametrage-applications/count")
    public ResponseEntity<Long> countParametrageApplications(ParametrageApplicationCriteria criteria) {
        log.debug("REST request to count ParametrageApplications by criteria: {}", criteria);
        return ResponseEntity.ok().body(parametrageApplicationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /parametrage-applications/:id : get the "id" parametrageApplication.
     *
     * @param id the id of the parametrageApplication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametrageApplication, or with status 404 (Not Found)
     */
    @GetMapping("/parametrage-applications/{id}")
    public ResponseEntity<ParametrageApplication> getParametrageApplication(@PathVariable Long id) {
        log.debug("REST request to get ParametrageApplication : {}", id);
        Optional<ParametrageApplication> parametrageApplication = parametrageApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametrageApplication);
    }

    /**
     * DELETE  /parametrage-applications/:id : delete the "id" parametrageApplication.
     *
     * @param id the id of the parametrageApplication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametrage-applications/{id}")
    public ResponseEntity<Void> deleteParametrageApplication(@PathVariable Long id) {
        log.debug("REST request to delete ParametrageApplication : {}", id);
        parametrageApplicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parametrage-applications?query=:query : search for the parametrageApplication corresponding
     * to the query.
     *
     * @param query the query of the parametrageApplication search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/parametrage-applications")
    public ResponseEntity<List<ParametrageApplication>> searchParametrageApplications(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParametrageApplications for query {}", query);
        Page<ParametrageApplication> page = parametrageApplicationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/parametrage-applications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
