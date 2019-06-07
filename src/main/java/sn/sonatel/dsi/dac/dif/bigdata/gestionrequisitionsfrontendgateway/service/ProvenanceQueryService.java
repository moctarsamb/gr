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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Provenance;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ProvenanceRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProvenanceSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ProvenanceCriteria;

/**
 * Service for executing complex queries for Provenance entities in the database.
 * The main input is a {@link ProvenanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Provenance} or a {@link Page} of {@link Provenance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProvenanceQueryService extends QueryService<Provenance> {

    private final Logger log = LoggerFactory.getLogger(ProvenanceQueryService.class);

    private final ProvenanceRepository provenanceRepository;

    private final ProvenanceSearchRepository provenanceSearchRepository;

    public ProvenanceQueryService(ProvenanceRepository provenanceRepository, ProvenanceSearchRepository provenanceSearchRepository) {
        this.provenanceRepository = provenanceRepository;
        this.provenanceSearchRepository = provenanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Provenance} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Provenance> findByCriteria(ProvenanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Provenance> specification = createSpecification(criteria);
        return provenanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Provenance} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Provenance> findByCriteria(ProvenanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Provenance> specification = createSpecification(criteria);
        return provenanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProvenanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Provenance> specification = createSpecification(criteria);
        return provenanceRepository.count(specification);
    }

    /**
     * Function to convert ProvenanceCriteria to a {@link Specification}
     */
    private Specification<Provenance> createSpecification(ProvenanceCriteria criteria) {
        Specification<Provenance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Provenance_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Provenance_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Provenance_.libelle));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Provenance_.email));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(Provenance_.requisitions, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
