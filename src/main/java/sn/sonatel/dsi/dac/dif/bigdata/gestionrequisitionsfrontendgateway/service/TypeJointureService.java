package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeJointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypeJointureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeJointureSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TypeJointure.
 */
@Service
@Transactional
public class TypeJointureService {

    private final Logger log = LoggerFactory.getLogger(TypeJointureService.class);

    private final TypeJointureRepository typeJointureRepository;

    private final TypeJointureSearchRepository typeJointureSearchRepository;

    public TypeJointureService(TypeJointureRepository typeJointureRepository, TypeJointureSearchRepository typeJointureSearchRepository) {
        this.typeJointureRepository = typeJointureRepository;
        this.typeJointureSearchRepository = typeJointureSearchRepository;
    }

    /**
     * Save a typeJointure.
     *
     * @param typeJointure the entity to save
     * @return the persisted entity
     */
    public TypeJointure save(TypeJointure typeJointure) {
        log.debug("Request to save TypeJointure : {}", typeJointure);
        TypeJointure result = typeJointureRepository.save(typeJointure);
        typeJointureSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the typeJointures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeJointure> findAll(Pageable pageable) {
        log.debug("Request to get all TypeJointures");
        return typeJointureRepository.findAll(pageable);
    }


    /**
     * Get one typeJointure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeJointure> findOne(Long id) {
        log.debug("Request to get TypeJointure : {}", id);
        return typeJointureRepository.findById(id);
    }

    /**
     * Delete the typeJointure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeJointure : {}", id);
        typeJointureRepository.deleteById(id);
        typeJointureSearchRepository.deleteById(id);
    }

    /**
     * Search for the typeJointure corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeJointure> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TypeJointures for query {}", query);
        return typeJointureSearchRepository.search(queryStringQuery(query), pageable);    }
}
