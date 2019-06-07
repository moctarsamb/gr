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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperateurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperateurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperateurCriteria;

/**
 * Service for executing complex queries for Operateur entities in the database.
 * The main input is a {@link OperateurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Operateur} or a {@link Page} of {@link Operateur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperateurQueryService extends QueryService<Operateur> {

    private final Logger log = LoggerFactory.getLogger(OperateurQueryService.class);

    private final OperateurRepository operateurRepository;

    private final OperateurSearchRepository operateurSearchRepository;

    public OperateurQueryService(OperateurRepository operateurRepository, OperateurSearchRepository operateurSearchRepository) {
        this.operateurRepository = operateurRepository;
        this.operateurSearchRepository = operateurSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Operateur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Operateur> findByCriteria(OperateurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Operateur> specification = createSpecification(criteria);
        return operateurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Operateur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Operateur> findByCriteria(OperateurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Operateur> specification = createSpecification(criteria);
        return operateurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperateurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Operateur> specification = createSpecification(criteria);
        return operateurRepository.count(specification);
    }

    /**
     * Function to convert OperateurCriteria to a {@link Specification}
     */
    private Specification<Operateur> createSpecification(OperateurCriteria criteria) {
        Specification<Operateur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Operateur_.id));
            }
            if (criteria.getOperateur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOperateur(), Operateur_.operateur));
            }
            if (criteria.getClauseId() != null) {
                specification = specification.and(buildSpecification(criteria.getClauseId(),
                    root -> root.join(Operateur_.clauses, JoinType.LEFT).get(Clause_.id)));
            }
        }
        return specification;
    }
}
