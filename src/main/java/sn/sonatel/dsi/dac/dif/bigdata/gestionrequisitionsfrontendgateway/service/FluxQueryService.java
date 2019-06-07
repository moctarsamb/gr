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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Flux;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FluxRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FluxSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FluxCriteria;

/**
 * Service for executing complex queries for Flux entities in the database.
 * The main input is a {@link FluxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Flux} or a {@link Page} of {@link Flux} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FluxQueryService extends QueryService<Flux> {

    private final Logger log = LoggerFactory.getLogger(FluxQueryService.class);

    private final FluxRepository fluxRepository;

    private final FluxSearchRepository fluxSearchRepository;

    public FluxQueryService(FluxRepository fluxRepository, FluxSearchRepository fluxSearchRepository) {
        this.fluxRepository = fluxRepository;
        this.fluxSearchRepository = fluxSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Flux} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Flux> findByCriteria(FluxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Flux> specification = createSpecification(criteria);
        return fluxRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Flux} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Flux> findByCriteria(FluxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Flux> specification = createSpecification(criteria);
        return fluxRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FluxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Flux> specification = createSpecification(criteria);
        return fluxRepository.count(specification);
    }

    /**
     * Function to convert FluxCriteria to a {@link Specification}
     */
    private Specification<Flux> createSpecification(FluxCriteria criteria) {
        Specification<Flux> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Flux_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Flux_.libelle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Flux_.description));
            }
            if (criteria.getColonneId() != null) {
                specification = specification.and(buildSpecification(criteria.getColonneId(),
                    root -> root.join(Flux_.colonnes, JoinType.LEFT).get(Colonne_.id)));
            }
            if (criteria.getDimensionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDimensionId(),
                    root -> root.join(Flux_.dimensions, JoinType.LEFT).get(Dimension_.id)));
            }
            if (criteria.getJointureId() != null) {
                specification = specification.and(buildSpecification(criteria.getJointureId(),
                    root -> root.join(Flux_.jointures, JoinType.LEFT).get(Jointure_.id)));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(Flux_.typeExtractions, JoinType.LEFT).get(TypeExtraction_.id)));
            }
            if (criteria.getBaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getBaseId(),
                    root -> root.join(Flux_.base, JoinType.LEFT).get(Base_.id)));
            }
        }
        return specification;
    }
}
