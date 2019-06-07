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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Jointure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.JointureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.JointureSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.JointureCriteria;

/**
 * Service for executing complex queries for Jointure entities in the database.
 * The main input is a {@link JointureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Jointure} or a {@link Page} of {@link Jointure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JointureQueryService extends QueryService<Jointure> {

    private final Logger log = LoggerFactory.getLogger(JointureQueryService.class);

    private final JointureRepository jointureRepository;

    private final JointureSearchRepository jointureSearchRepository;

    public JointureQueryService(JointureRepository jointureRepository, JointureSearchRepository jointureSearchRepository) {
        this.jointureRepository = jointureRepository;
        this.jointureSearchRepository = jointureSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Jointure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Jointure> findByCriteria(JointureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Jointure> specification = createSpecification(criteria);
        return jointureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Jointure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Jointure> findByCriteria(JointureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Jointure> specification = createSpecification(criteria);
        return jointureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JointureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Jointure> specification = createSpecification(criteria);
        return jointureRepository.count(specification);
    }

    /**
     * Function to convert JointureCriteria to a {@link Specification}
     */
    private Specification<Jointure> createSpecification(JointureCriteria criteria) {
        Specification<Jointure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Jointure_.id));
            }
            if (criteria.getCritereId() != null) {
                specification = specification.and(buildSpecification(criteria.getCritereId(),
                    root -> root.join(Jointure_.critere, JoinType.LEFT).get(Critere_.id)));
            }
            if (criteria.getFluxId() != null) {
                specification = specification.and(buildSpecification(criteria.getFluxId(),
                    root -> root.join(Jointure_.flux, JoinType.LEFT).get(Flux_.id)));
            }
            if (criteria.getTypeJointureId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeJointureId(),
                    root -> root.join(Jointure_.typeJointure, JoinType.LEFT).get(TypeJointure_.id)));
            }
        }
        return specification;
    }
}
