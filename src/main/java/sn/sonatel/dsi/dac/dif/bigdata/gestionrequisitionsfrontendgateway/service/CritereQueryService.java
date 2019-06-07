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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Critere;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.CritereRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.CritereSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.CritereCriteria;

/**
 * Service for executing complex queries for Critere entities in the database.
 * The main input is a {@link CritereCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Critere} or a {@link Page} of {@link Critere} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CritereQueryService extends QueryService<Critere> {

    private final Logger log = LoggerFactory.getLogger(CritereQueryService.class);

    private final CritereRepository critereRepository;

    private final CritereSearchRepository critereSearchRepository;

    public CritereQueryService(CritereRepository critereRepository, CritereSearchRepository critereSearchRepository) {
        this.critereRepository = critereRepository;
        this.critereSearchRepository = critereSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Critere} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Critere> findByCriteria(CritereCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Critere> specification = createSpecification(criteria);
        return critereRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Critere} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Critere> findByCriteria(CritereCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Critere> specification = createSpecification(criteria);
        return critereRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CritereCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Critere> specification = createSpecification(criteria);
        return critereRepository.count(specification);
    }

    /**
     * Function to convert CritereCriteria to a {@link Specification}
     */
    private Specification<Critere> createSpecification(CritereCriteria criteria) {
        Specification<Critere> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Critere_.id));
            }
            if (criteria.getJointureId() != null) {
                specification = specification.and(buildSpecification(criteria.getJointureId(),
                    root -> root.join(Critere_.jointures, JoinType.LEFT).get(Jointure_.id)));
            }
            if (criteria.getClauseId() != null) {
                specification = specification.and(buildSpecification(criteria.getClauseId(),
                    root -> root.join(Critere_.clause, JoinType.LEFT).get(Clause_.id)));
            }
            if (criteria.getOperateurLogiqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperateurLogiqueId(),
                    root -> root.join(Critere_.operateurLogique, JoinType.LEFT).get(OperateurLogique_.id)));
            }
        }
        return specification;
    }
}
