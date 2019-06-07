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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Fonction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FonctionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FonctionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FonctionCriteria;

/**
 * Service for executing complex queries for Fonction entities in the database.
 * The main input is a {@link FonctionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Fonction} or a {@link Page} of {@link Fonction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FonctionQueryService extends QueryService<Fonction> {

    private final Logger log = LoggerFactory.getLogger(FonctionQueryService.class);

    private final FonctionRepository fonctionRepository;

    private final FonctionSearchRepository fonctionSearchRepository;

    public FonctionQueryService(FonctionRepository fonctionRepository, FonctionSearchRepository fonctionSearchRepository) {
        this.fonctionRepository = fonctionRepository;
        this.fonctionSearchRepository = fonctionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Fonction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Fonction> findByCriteria(FonctionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Fonction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Fonction> findByCriteria(FonctionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FonctionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fonction> specification = createSpecification(criteria);
        return fonctionRepository.count(specification);
    }

    /**
     * Function to convert FonctionCriteria to a {@link Specification}
     */
    private Specification<Fonction> createSpecification(FonctionCriteria criteria) {
        Specification<Fonction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Fonction_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Fonction_.nom));
            }
            if (criteria.getMonitorId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonitorId(),
                    root -> root.join(Fonction_.monitors, JoinType.LEFT).get(Monitor_.id)));
            }
            if (criteria.getOperandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperandeId(),
                    root -> root.join(Fonction_.operandes, JoinType.LEFT).get(Operande_.id)));
            }
        }
        return specification;
    }
}
