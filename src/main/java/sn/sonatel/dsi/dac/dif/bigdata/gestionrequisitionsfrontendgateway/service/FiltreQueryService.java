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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filtre;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FiltreRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FiltreSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FiltreCriteria;

/**
 * Service for executing complex queries for Filtre entities in the database.
 * The main input is a {@link FiltreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Filtre} or a {@link Page} of {@link Filtre} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FiltreQueryService extends QueryService<Filtre> {

    private final Logger log = LoggerFactory.getLogger(FiltreQueryService.class);

    private final FiltreRepository filtreRepository;

    private final FiltreSearchRepository filtreSearchRepository;

    public FiltreQueryService(FiltreRepository filtreRepository, FiltreSearchRepository filtreSearchRepository) {
        this.filtreRepository = filtreRepository;
        this.filtreSearchRepository = filtreSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Filtre} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Filtre> findByCriteria(FiltreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Filtre> specification = createSpecification(criteria);
        return filtreRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Filtre} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Filtre> findByCriteria(FiltreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Filtre> specification = createSpecification(criteria);
        return filtreRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FiltreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Filtre> specification = createSpecification(criteria);
        return filtreRepository.count(specification);
    }

    /**
     * Function to convert FiltreCriteria to a {@link Specification}
     */
    private Specification<Filtre> createSpecification(FiltreCriteria criteria) {
        Specification<Filtre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Filtre_.id));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(Filtre_.typeExtractions, JoinType.LEFT).get(TypeExtraction_.id)));
            }
            if (criteria.getClauseId() != null) {
                specification = specification.and(buildSpecification(criteria.getClauseId(),
                    root -> root.join(Filtre_.clauses, JoinType.LEFT).get(Clause_.id)));
            }
            if (criteria.getOperateurLogiqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperateurLogiqueId(),
                    root -> root.join(Filtre_.operateurLogique, JoinType.LEFT).get(OperateurLogique_.id)));
            }
        }
        return specification;
    }
}
