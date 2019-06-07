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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Dimension;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.DimensionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DimensionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.DimensionCriteria;

/**
 * Service for executing complex queries for Dimension entities in the database.
 * The main input is a {@link DimensionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Dimension} or a {@link Page} of {@link Dimension} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DimensionQueryService extends QueryService<Dimension> {

    private final Logger log = LoggerFactory.getLogger(DimensionQueryService.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionSearchRepository dimensionSearchRepository;

    public DimensionQueryService(DimensionRepository dimensionRepository, DimensionSearchRepository dimensionSearchRepository) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionSearchRepository = dimensionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Dimension} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Dimension> findByCriteria(DimensionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Dimension} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Dimension> findByCriteria(DimensionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DimensionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.count(specification);
    }

    /**
     * Function to convert DimensionCriteria to a {@link Specification}
     */
    private Specification<Dimension> createSpecification(DimensionCriteria criteria) {
        Specification<Dimension> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Dimension_.id));
            }
            if (criteria.getMonitorId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonitorId(),
                    root -> root.join(Dimension_.monitors, JoinType.LEFT).get(Monitor_.id)));
            }
            if (criteria.getBaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getBaseId(),
                    root -> root.join(Dimension_.base, JoinType.LEFT).get(Base_.id)));
            }
            if (criteria.getFluxId() != null) {
                specification = specification.and(buildSpecification(criteria.getFluxId(),
                    root -> root.join(Dimension_.flux, JoinType.LEFT).get(Flux_.id)));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(Dimension_.typeExtraction, JoinType.LEFT).get(TypeExtraction_.id)));
            }
        }
        return specification;
    }
}
