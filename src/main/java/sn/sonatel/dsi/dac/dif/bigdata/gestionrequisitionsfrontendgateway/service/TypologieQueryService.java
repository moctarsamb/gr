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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Typologie;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypologieRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypologieSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypologieCriteria;

/**
 * Service for executing complex queries for Typologie entities in the database.
 * The main input is a {@link TypologieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Typologie} or a {@link Page} of {@link Typologie} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypologieQueryService extends QueryService<Typologie> {

    private final Logger log = LoggerFactory.getLogger(TypologieQueryService.class);

    private final TypologieRepository typologieRepository;

    private final TypologieSearchRepository typologieSearchRepository;

    public TypologieQueryService(TypologieRepository typologieRepository, TypologieSearchRepository typologieSearchRepository) {
        this.typologieRepository = typologieRepository;
        this.typologieSearchRepository = typologieSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Typologie} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Typologie> findByCriteria(TypologieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Typologie> specification = createSpecification(criteria);
        return typologieRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Typologie} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Typologie> findByCriteria(TypologieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Typologie> specification = createSpecification(criteria);
        return typologieRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypologieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Typologie> specification = createSpecification(criteria);
        return typologieRepository.count(specification);
    }

    /**
     * Function to convert TypologieCriteria to a {@link Specification}
     */
    private Specification<Typologie> createSpecification(TypologieCriteria criteria) {
        Specification<Typologie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Typologie_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Typologie_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Typologie_.libelle));
            }
            if (criteria.getCondition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCondition(), Typologie_.condition));
            }
            if (criteria.getTraitement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTraitement(), Typologie_.traitement));
            }
        }
        return specification;
    }
}
