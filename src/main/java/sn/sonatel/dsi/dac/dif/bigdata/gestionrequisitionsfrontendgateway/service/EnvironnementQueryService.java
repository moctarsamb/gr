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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Environnement;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.EnvironnementRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvironnementSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.EnvironnementCriteria;

/**
 * Service for executing complex queries for Environnement entities in the database.
 * The main input is a {@link EnvironnementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Environnement} or a {@link Page} of {@link Environnement} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnvironnementQueryService extends QueryService<Environnement> {

    private final Logger log = LoggerFactory.getLogger(EnvironnementQueryService.class);

    private final EnvironnementRepository environnementRepository;

    private final EnvironnementSearchRepository environnementSearchRepository;

    public EnvironnementQueryService(EnvironnementRepository environnementRepository, EnvironnementSearchRepository environnementSearchRepository) {
        this.environnementRepository = environnementRepository;
        this.environnementSearchRepository = environnementSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Environnement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Environnement> findByCriteria(EnvironnementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Environnement> specification = createSpecification(criteria);
        return environnementRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Environnement} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Environnement> findByCriteria(EnvironnementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Environnement> specification = createSpecification(criteria);
        return environnementRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnvironnementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Environnement> specification = createSpecification(criteria);
        return environnementRepository.count(specification);
    }

    /**
     * Function to convert EnvironnementCriteria to a {@link Specification}
     */
    private Specification<Environnement> createSpecification(EnvironnementCriteria criteria) {
        Specification<Environnement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Environnement_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Environnement_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Environnement_.libelle));
            }
            if (criteria.getChampsRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getChampsRechercheId(),
                    root -> root.join(Environnement_.champsRecherches, JoinType.LEFT).get(ChampsRecherche_.id)));
            }
            if (criteria.getFilialeId() != null) {
                specification = specification.and(buildSpecification(criteria.getFilialeId(),
                    root -> root.join(Environnement_.filiales, JoinType.LEFT).get(Filiale_.id)));
            }
        }
        return specification;
    }
}
