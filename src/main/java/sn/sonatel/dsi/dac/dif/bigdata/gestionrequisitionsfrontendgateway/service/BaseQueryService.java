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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Base;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.BaseRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.BaseSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.BaseCriteria;

/**
 * Service for executing complex queries for Base entities in the database.
 * The main input is a {@link BaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Base} or a {@link Page} of {@link Base} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BaseQueryService extends QueryService<Base> {

    private final Logger log = LoggerFactory.getLogger(BaseQueryService.class);

    private final BaseRepository baseRepository;

    private final BaseSearchRepository baseSearchRepository;

    public BaseQueryService(BaseRepository baseRepository, BaseSearchRepository baseSearchRepository) {
        this.baseRepository = baseRepository;
        this.baseSearchRepository = baseSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Base} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Base> findByCriteria(BaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Base> specification = createSpecification(criteria);
        return baseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Base} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Base> findByCriteria(BaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Base> specification = createSpecification(criteria);
        return baseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Base> specification = createSpecification(criteria);
        return baseRepository.count(specification);
    }

    /**
     * Function to convert BaseCriteria to a {@link Specification}
     */
    private Specification<Base> createSpecification(BaseCriteria criteria) {
        Specification<Base> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Base_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Base_.libelle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Base_.description));
            }
            if (criteria.getDimensionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDimensionId(),
                    root -> root.join(Base_.dimensions, JoinType.LEFT).get(Dimension_.id)));
            }
            if (criteria.getFluxId() != null) {
                specification = specification.and(buildSpecification(criteria.getFluxId(),
                    root -> root.join(Base_.fluxes, JoinType.LEFT).get(Flux_.id)));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(Base_.typeExtractions, JoinType.LEFT).get(TypeExtraction_.id)));
            }
        }
        return specification;
    }
}
