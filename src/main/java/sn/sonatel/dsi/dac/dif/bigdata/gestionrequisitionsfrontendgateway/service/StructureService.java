package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.StructureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.StructureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Structure.
 */
@Service
@Transactional
public class StructureService {

    private final Logger log = LoggerFactory.getLogger(StructureService.class);

    private final StructureRepository structureRepository;

    private final StructureSearchRepository structureSearchRepository;

    public StructureService(StructureRepository structureRepository, StructureSearchRepository structureSearchRepository) {
        this.structureRepository = structureRepository;
        this.structureSearchRepository = structureSearchRepository;
    }

    /**
     * Save a structure.
     *
     * @param structure the entity to save
     * @return the persisted entity
     */
    public Structure save(Structure structure) {
        log.debug("Request to save Structure : {}", structure);
        Structure result = structureRepository.save(structure);
        structureSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the structures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Structure> findAll(Pageable pageable) {
        log.debug("Request to get all Structures");
        return structureRepository.findAll(pageable);
    }


    /**
     * Get one structure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Structure> findOne(Long id) {
        log.debug("Request to get Structure : {}", id);
        return structureRepository.findById(id);
    }

    /**
     * Delete the structure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Structure : {}", id);
        structureRepository.deleteById(id);
        structureSearchRepository.deleteById(id);
    }

    /**
     * Search for the structure corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Structure> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Structures for query {}", query);
        return structureSearchRepository.search(queryStringQuery(query), pageable);    }
}
