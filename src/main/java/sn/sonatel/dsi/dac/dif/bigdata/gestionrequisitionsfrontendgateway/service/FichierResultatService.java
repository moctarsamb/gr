package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FichierResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FichierResultatSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FichierResultat.
 */
@Service
@Transactional
public class FichierResultatService {

    private final Logger log = LoggerFactory.getLogger(FichierResultatService.class);

    private final FichierResultatRepository fichierResultatRepository;

    private final FichierResultatSearchRepository fichierResultatSearchRepository;

    public FichierResultatService(FichierResultatRepository fichierResultatRepository, FichierResultatSearchRepository fichierResultatSearchRepository) {
        this.fichierResultatRepository = fichierResultatRepository;
        this.fichierResultatSearchRepository = fichierResultatSearchRepository;
    }

    /**
     * Save a fichierResultat.
     *
     * @param fichierResultat the entity to save
     * @return the persisted entity
     */
    public FichierResultat save(FichierResultat fichierResultat) {
        log.debug("Request to save FichierResultat : {}", fichierResultat);
        FichierResultat result = fichierResultatRepository.save(fichierResultat);
        fichierResultatSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the fichierResultats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FichierResultat> findAll(Pageable pageable) {
        log.debug("Request to get all FichierResultats");
        return fichierResultatRepository.findAll(pageable);
    }


    /**
     * Get one fichierResultat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FichierResultat> findOne(Long id) {
        log.debug("Request to get FichierResultat : {}", id);
        return fichierResultatRepository.findById(id);
    }

    /**
     * Delete the fichierResultat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FichierResultat : {}", id);
        fichierResultatRepository.deleteById(id);
        fichierResultatSearchRepository.deleteById(id);
    }

    /**
     * Search for the fichierResultat corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FichierResultat> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FichierResultats for query {}", query);
        return fichierResultatSearchRepository.search(queryStringQuery(query), pageable);    }
}
