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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Clause;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ClauseRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ClauseSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ClauseCriteria;

/**
 * Service for executing complex queries for Clause entities in the database.
 * The main input is a {@link ClauseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Clause} or a {@link Page} of {@link Clause} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClauseQueryService extends QueryService<Clause> {

    private final Logger log = LoggerFactory.getLogger(ClauseQueryService.class);

    private final ClauseRepository clauseRepository;

    private final ClauseSearchRepository clauseSearchRepository;

    public ClauseQueryService(ClauseRepository clauseRepository, ClauseSearchRepository clauseSearchRepository) {
        this.clauseRepository = clauseRepository;
        this.clauseSearchRepository = clauseSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Clause} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Clause> findByCriteria(ClauseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Clause> specification = createSpecification(criteria);
        return clauseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Clause} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Clause> findByCriteria(ClauseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Clause> specification = createSpecification(criteria);
        return clauseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClauseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Clause> specification = createSpecification(criteria);
        return clauseRepository.count(specification);
    }

    /**
     * Function to convert ClauseCriteria to a {@link Specification}
     */
    private Specification<Clause> createSpecification(ClauseCriteria criteria) {
        Specification<Clause> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Clause_.id));
            }
            if (criteria.getPrefixe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrefixe(), Clause_.prefixe));
            }
            if (criteria.getSuffixe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuffixe(), Clause_.suffixe));
            }
            if (criteria.getCritereId() != null) {
                specification = specification.and(buildSpecification(criteria.getCritereId(),
                    root -> root.join(Clause_.criteres, JoinType.LEFT).get(Critere_.id)));
            }
            if (criteria.getOperandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperandeId(),
                    root -> root.join(Clause_.operandes, JoinType.LEFT).get(Operande_.id)));
            }
            if (criteria.getOperateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperateurId(),
                    root -> root.join(Clause_.operateur, JoinType.LEFT).get(Operateur_.id)));
            }
            if (criteria.getFiltreId() != null) {
                specification = specification.and(buildSpecification(criteria.getFiltreId(),
                    root -> root.join(Clause_.filtres, JoinType.LEFT).get(Filtre_.id)));
            }
        }
        return specification;
    }
}
