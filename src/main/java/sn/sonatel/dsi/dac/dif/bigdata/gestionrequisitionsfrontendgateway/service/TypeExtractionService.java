package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypeExtractionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeExtractionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TypeExtraction.
 */
@Service
@Transactional
public class TypeExtractionService {

    private final Logger log = LoggerFactory.getLogger(TypeExtractionService.class);

    private final TypeExtractionRepository typeExtractionRepository;

    private final TypeExtractionSearchRepository typeExtractionSearchRepository;

    public TypeExtractionService(TypeExtractionRepository typeExtractionRepository, TypeExtractionSearchRepository typeExtractionSearchRepository) {
        this.typeExtractionRepository = typeExtractionRepository;
        this.typeExtractionSearchRepository = typeExtractionSearchRepository;
    }

    /**
     * Save a typeExtraction.
     *
     * @param typeExtraction the entity to save
     * @return the persisted entity
     */
    public TypeExtraction save(TypeExtraction typeExtraction) {
        log.debug("Request to save TypeExtraction : {}", typeExtraction);
        TypeExtraction result = typeExtractionRepository.save(typeExtraction);
        typeExtractionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the typeExtractions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeExtraction> findAll(Pageable pageable) {
        log.debug("Request to get all TypeExtractions");
        return typeExtractionRepository.findAll(pageable);
    }

    /**
     * Get all the TypeExtraction with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TypeExtraction> findAllWithEagerRelationships(Pageable pageable) {
        return typeExtractionRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one typeExtraction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeExtraction> findOne(Long id) {
        log.debug("Request to get TypeExtraction : {}", id);
        return typeExtractionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the typeExtraction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeExtraction : {}", id);
        typeExtractionRepository.deleteById(id);
        typeExtractionSearchRepository.deleteById(id);
    }

    /**
     * Search for the typeExtraction corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeExtraction> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TypeExtractions for query {}", query);
        return typeExtractionSearchRepository.search(queryStringQuery(query), pageable);    }
}
