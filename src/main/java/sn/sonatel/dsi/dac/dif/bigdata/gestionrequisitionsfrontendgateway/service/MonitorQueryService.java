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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Monitor;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.MonitorRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.MonitorSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.MonitorCriteria;

/**
 * Service for executing complex queries for Monitor entities in the database.
 * The main input is a {@link MonitorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Monitor} or a {@link Page} of {@link Monitor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MonitorQueryService extends QueryService<Monitor> {

    private final Logger log = LoggerFactory.getLogger(MonitorQueryService.class);

    private final MonitorRepository monitorRepository;

    private final MonitorSearchRepository monitorSearchRepository;

    public MonitorQueryService(MonitorRepository monitorRepository, MonitorSearchRepository monitorSearchRepository) {
        this.monitorRepository = monitorRepository;
        this.monitorSearchRepository = monitorSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Monitor} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Monitor> findByCriteria(MonitorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Monitor> specification = createSpecification(criteria);
        return monitorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Monitor} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Monitor> findByCriteria(MonitorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Monitor> specification = createSpecification(criteria);
        return monitorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MonitorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Monitor> specification = createSpecification(criteria);
        return monitorRepository.count(specification);
    }

    /**
     * Function to convert MonitorCriteria to a {@link Specification}
     */
    private Specification<Monitor> createSpecification(MonitorCriteria criteria) {
        Specification<Monitor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Monitor_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Monitor_.type));
            }
            if (criteria.getColonneId() != null) {
                specification = specification.and(buildSpecification(criteria.getColonneId(),
                    root -> root.join(Monitor_.colonne, JoinType.LEFT).get(Colonne_.id)));
            }
            if (criteria.getFonctionId() != null) {
                specification = specification.and(buildSpecification(criteria.getFonctionId(),
                    root -> root.join(Monitor_.fonction, JoinType.LEFT).get(Fonction_.id)));
            }
            if (criteria.getDimensionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDimensionId(),
                    root -> root.join(Monitor_.dimensions, JoinType.LEFT).get(Dimension_.id)));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(Monitor_.typeExtractions, JoinType.LEFT).get(TypeExtraction_.id)));
            }
        }
        return specification;
    }
}
