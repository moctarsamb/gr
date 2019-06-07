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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Valeur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ValeurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ValeurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ValeurCriteria;

/**
 * Service for executing complex queries for Valeur entities in the database.
 * The main input is a {@link ValeurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Valeur} or a {@link Page} of {@link Valeur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ValeurQueryService extends QueryService<Valeur> {

    private final Logger log = LoggerFactory.getLogger(ValeurQueryService.class);

    private final ValeurRepository valeurRepository;

    private final ValeurSearchRepository valeurSearchRepository;

    public ValeurQueryService(ValeurRepository valeurRepository, ValeurSearchRepository valeurSearchRepository) {
        this.valeurRepository = valeurRepository;
        this.valeurSearchRepository = valeurSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Valeur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Valeur> findByCriteria(ValeurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Valeur> specification = createSpecification(criteria);
        return valeurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Valeur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Valeur> findByCriteria(ValeurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Valeur> specification = createSpecification(criteria);
        return valeurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ValeurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Valeur> specification = createSpecification(criteria);
        return valeurRepository.count(specification);
    }

    /**
     * Function to convert ValeurCriteria to a {@link Specification}
     */
    private Specification<Valeur> createSpecification(ValeurCriteria criteria) {
        Specification<Valeur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Valeur_.id));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValeur(), Valeur_.valeur));
            }
            if (criteria.getOperandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperandeId(),
                    root -> root.join(Valeur_.operandes, JoinType.LEFT).get(Operande_.id)));
            }
        }
        return specification;
    }
}
