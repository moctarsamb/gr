package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeJointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypeJointureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeJointureSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypeJointureCriteria;

/**
 * Service for executing complex queries for TypeJointure entities in the database.
 * The main input is a {@link TypeJointureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeJointure} or a {@link Page} of {@link TypeJointure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeJointureQueryService extends QueryService<TypeJointure> {

    private final Logger log = LoggerFactory.getLogger(TypeJointureQueryService.class);

    private final TypeJointureRepository typeJointureRepository;

    private final TypeJointureSearchRepository typeJointureSearchRepository;

    public TypeJointureQueryService(TypeJointureRepository typeJointureRepository, TypeJointureSearchRepository typeJointureSearchRepository) {
        this.typeJointureRepository = typeJointureRepository;
        this.typeJointureSearchRepository = typeJointureSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TypeJointure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeJointure> findByCriteria(TypeJointureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeJointure> specification = createSpecification(criteria);
        return typeJointureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TypeJointure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeJointure> findByCriteria(TypeJointureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeJointure> specification = createSpecification(criteria);
        return typeJointureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeJointureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeJointure> specification = createSpecification(criteria);
        return typeJointureRepository.count(specification);
    }

    /**
     * Function to convert TypeJointureCriteria to a {@link Specification}
     */
    private Specification<TypeJointure> createSpecification(TypeJointureCriteria criteria) {
        Specification<TypeJointure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeJointure_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), TypeJointure_.type));
            }
            if (criteria.getJointureId() != null) {
                specification = specification.and(buildSpecification(criteria.getJointureId(),
                    root -> root.join(TypeJointure_.jointures, JoinType.LEFT).get(Jointure_.id)));
            }
        }
        return specification;
    }
}
