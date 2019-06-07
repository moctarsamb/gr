package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.JointureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.JointureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Jointure.
 */
@Service
@Transactional
public class JointureService {

    private final Logger log = LoggerFactory.getLogger(JointureService.class);

    private final JointureRepository jointureRepository;

    private final JointureSearchRepository jointureSearchRepository;

    public JointureService(JointureRepository jointureRepository, JointureSearchRepository jointureSearchRepository) {
        this.jointureRepository = jointureRepository;
        this.jointureSearchRepository = jointureSearchRepository;
    }

    /**
     * Save a jointure.
     *
     * @param jointure the entity to save
     * @return the persisted entity
     */
    public Jointure save(Jointure jointure) {
        log.debug("Request to save Jointure : {}", jointure);
        Jointure result = jointureRepository.save(jointure);
        jointureSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the jointures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Jointure> findAll(Pageable pageable) {
        log.debug("Request to get all Jointures");
        return jointureRepository.findAll(pageable);
    }


    /**
     * Get one jointure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Jointure> findOne(Long id) {
        log.debug("Request to get Jointure : {}", id);
        return jointureRepository.findById(id);
    }

    /**
     * Delete the jointure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Jointure : {}", id);
        jointureRepository.deleteById(id);
        jointureSearchRepository.deleteById(id);
    }

    /**
     * Search for the jointure corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Jointure> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Jointures for query {}", query);
        return jointureSearchRepository.search(queryStringQuery(query), pageable);    }
}
