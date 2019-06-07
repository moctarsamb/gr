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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Operande;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.OperandeRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.OperandeSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.OperandeCriteria;

/**
 * Service for executing complex queries for Operande entities in the database.
 * The main input is a {@link OperandeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Operande} or a {@link Page} of {@link Operande} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperandeQueryService extends QueryService<Operande> {

    private final Logger log = LoggerFactory.getLogger(OperandeQueryService.class);

    private final OperandeRepository operandeRepository;

    private final OperandeSearchRepository operandeSearchRepository;

    public OperandeQueryService(OperandeRepository operandeRepository, OperandeSearchRepository operandeSearchRepository) {
        this.operandeRepository = operandeRepository;
        this.operandeSearchRepository = operandeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Operande} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Operande> findByCriteria(OperandeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Operande> specification = createSpecification(criteria);
        return operandeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Operande} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Operande> findByCriteria(OperandeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Operande> specification = createSpecification(criteria);
        return operandeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperandeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Operande> specification = createSpecification(criteria);
        return operandeRepository.count(specification);
    }

    /**
     * Function to convert OperandeCriteria to a {@link Specification}
     */
    private Specification<Operande> createSpecification(OperandeCriteria criteria) {
        Specification<Operande> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Operande_.id));
            }
            if (criteria.getColonneId() != null) {
                specification = specification.and(buildSpecification(criteria.getColonneId(),
                    root -> root.join(Operande_.colonnes, JoinType.LEFT).get(Colonne_.id)));
            }
            if (criteria.getValeurId() != null) {
                specification = specification.and(buildSpecification(criteria.getValeurId(),
                    root -> root.join(Operande_.valeurs, JoinType.LEFT).get(Valeur_.id)));
            }
            if (criteria.getFonctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getFonctionId(),
                    root -> root.join(Operande_.fonction, JoinType.LEFT).get(Fonction_.id)));
            }
            if (criteria.getClauseId() != null) {
                specification = specification.and(buildSpecification(criteria.getClauseId(),
                    root -> root.join(Operande_.clauses, JoinType.LEFT).get(Clause_.id)));
            }
        }
        return specification;
    }
}
