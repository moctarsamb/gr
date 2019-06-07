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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Structure;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.StructureRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.StructureSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.StructureCriteria;

/**
 * Service for executing complex queries for Structure entities in the database.
 * The main input is a {@link StructureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Structure} or a {@link Page} of {@link Structure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StructureQueryService extends QueryService<Structure> {

    private final Logger log = LoggerFactory.getLogger(StructureQueryService.class);

    private final StructureRepository structureRepository;

    private final StructureSearchRepository structureSearchRepository;

    public StructureQueryService(StructureRepository structureRepository, StructureSearchRepository structureSearchRepository) {
        this.structureRepository = structureRepository;
        this.structureSearchRepository = structureSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Structure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Structure> findByCriteria(StructureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Structure> specification = createSpecification(criteria);
        return structureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Structure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Structure> findByCriteria(StructureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Structure> specification = createSpecification(criteria);
        return structureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StructureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Structure> specification = createSpecification(criteria);
        return structureRepository.count(specification);
    }

    /**
     * Function to convert StructureCriteria to a {@link Specification}
     */
    private Specification<Structure> createSpecification(StructureCriteria criteria) {
        Specification<Structure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Structure_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Structure_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Structure_.libelle));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(Structure_.requisitions, JoinType.LEFT).get(Requisition_.id)));
            }
            if (criteria.getFilialeId() != null) {
                specification = specification.and(buildSpecification(criteria.getFilialeId(),
                    root -> root.join(Structure_.filiale, JoinType.LEFT).get(Filiale_.id)));
            }
        }
        return specification;
    }
}
