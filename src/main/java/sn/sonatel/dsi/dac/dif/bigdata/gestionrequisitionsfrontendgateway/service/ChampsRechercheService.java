package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ChampsRechercheRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ChampsRechercheSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ChampsRecherche.
 */
@Service
@Transactional
public class ChampsRechercheService {

    private final Logger log = LoggerFactory.getLogger(ChampsRechercheService.class);

    private final ChampsRechercheRepository champsRechercheRepository;

    private final ChampsRechercheSearchRepository champsRechercheSearchRepository;

    public ChampsRechercheService(ChampsRechercheRepository champsRechercheRepository, ChampsRechercheSearchRepository champsRechercheSearchRepository) {
        this.champsRechercheRepository = champsRechercheRepository;
        this.champsRechercheSearchRepository = champsRechercheSearchRepository;
    }

    /**
     * Save a champsRecherche.
     *
     * @param champsRecherche the entity to save
     * @return the persisted entity
     */
    public ChampsRecherche save(ChampsRecherche champsRecherche) {
        log.debug("Request to save ChampsRecherche : {}", champsRecherche);
        ChampsRecherche result = champsRechercheRepository.save(champsRecherche);
        champsRechercheSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the champsRecherches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChampsRecherche> findAll(Pageable pageable) {
        log.debug("Request to get all ChampsRecherches");
        return champsRechercheRepository.findAll(pageable);
    }



    /**
     *  get all the champsRecherches where Resultat is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ChampsRecherche> findAllWhereResultatIsNull() {
        log.debug("Request to get all champsRecherches where Resultat is null");
        return StreamSupport
            .stream(champsRechercheRepository.findAll().spliterator(), false)
            .filter(champsRecherche -> champsRecherche.getResultat() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one champsRecherche by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChampsRecherche> findOne(Long id) {
        log.debug("Request to get ChampsRecherche : {}", id);
        return champsRechercheRepository.findById(id);
    }

    /**
     * Delete the champsRecherche by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChampsRecherche : {}", id);
        champsRechercheRepository.deleteById(id);
        champsRechercheSearchRepository.deleteById(id);
    }

    /**
     * Search for the champsRecherche corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChampsRecherche> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChampsRecherches for query {}", query);
        return champsRechercheSearchRepository.search(queryStringQuery(query), pageable);    }
}
