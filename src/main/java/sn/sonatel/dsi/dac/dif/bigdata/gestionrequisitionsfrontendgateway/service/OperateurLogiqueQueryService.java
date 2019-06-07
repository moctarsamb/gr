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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.OperateurLogique;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperateurLogiqueRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurLogiqueSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperateurLogiqueCriteria;

/**
 * Service for executing complex queries for OperateurLogique entities in the database.
 * The main input is a {@link OperateurLogiqueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OperateurLogique} or a {@link Page} of {@link OperateurLogique} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperateurLogiqueQueryService extends QueryService<OperateurLogique> {

    private final Logger log = LoggerFactory.getLogger(OperateurLogiqueQueryService.class);

    private final OperateurLogiqueRepository operateurLogiqueRepository;

    private final OperateurLogiqueSearchRepository operateurLogiqueSearchRepository;

    public OperateurLogiqueQueryService(OperateurLogiqueRepository operateurLogiqueRepository, OperateurLogiqueSearchRepository operateurLogiqueSearchRepository) {
        this.operateurLogiqueRepository = operateurLogiqueRepository;
        this.operateurLogiqueSearchRepository = operateurLogiqueSearchRepository;
    }

    /**
     * Return a {@link List} of {@link OperateurLogique} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OperateurLogique> findByCriteria(OperateurLogiqueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OperateurLogique> specification = createSpecification(criteria);
        return operateurLogiqueRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OperateurLogique} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OperateurLogique> findByCriteria(OperateurLogiqueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OperateurLogique> specification = createSpecification(criteria);
        return operateurLogiqueRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperateurLogiqueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OperateurLogique> specification = createSpecification(criteria);
        return operateurLogiqueRepository.count(specification);
    }

    /**
     * Function to convert OperateurLogiqueCriteria to a {@link Specification}
     */
    private Specification<OperateurLogique> createSpecification(OperateurLogiqueCriteria criteria) {
        Specification<OperateurLogique> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OperateurLogique_.id));
            }
            if (criteria.getOperateurLogique() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOperateurLogique(), OperateurLogique_.operateurLogique));
            }
            if (criteria.getCritereId() != null) {
                specification = specification.and(buildSpecification(criteria.getCritereId(),
                    root -> root.join(OperateurLogique_.criteres, JoinType.LEFT).get(Critere_.id)));
            }
            if (criteria.getFiltreId() != null) {
                specification = specification.and(buildSpecification(criteria.getFiltreId(),
                    root -> root.join(OperateurLogique_.filtres, JoinType.LEFT).get(Filtre_.id)));
            }
        }
        return specification;
    }
}
