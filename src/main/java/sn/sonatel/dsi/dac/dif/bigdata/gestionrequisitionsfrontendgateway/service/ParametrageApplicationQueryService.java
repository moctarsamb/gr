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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ParametrageApplication;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ParametrageApplicationRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ParametrageApplicationSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ParametrageApplicationCriteria;

/**
 * Service for executing complex queries for ParametrageApplication entities in the database.
 * The main input is a {@link ParametrageApplicationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParametrageApplication} or a {@link Page} of {@link ParametrageApplication} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParametrageApplicationQueryService extends QueryService<ParametrageApplication> {

    private final Logger log = LoggerFactory.getLogger(ParametrageApplicationQueryService.class);

    private final ParametrageApplicationRepository parametrageApplicationRepository;

    private final ParametrageApplicationSearchRepository parametrageApplicationSearchRepository;

    public ParametrageApplicationQueryService(ParametrageApplicationRepository parametrageApplicationRepository, ParametrageApplicationSearchRepository parametrageApplicationSearchRepository) {
        this.parametrageApplicationRepository = parametrageApplicationRepository;
        this.parametrageApplicationSearchRepository = parametrageApplicationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ParametrageApplication} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParametrageApplication> findByCriteria(ParametrageApplicationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ParametrageApplication> specification = createSpecification(criteria);
        return parametrageApplicationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ParametrageApplication} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParametrageApplication> findByCriteria(ParametrageApplicationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ParametrageApplication> specification = createSpecification(criteria);
        return parametrageApplicationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParametrageApplicationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ParametrageApplication> specification = createSpecification(criteria);
        return parametrageApplicationRepository.count(specification);
    }

    /**
     * Function to convert ParametrageApplicationCriteria to a {@link Specification}
     */
    private Specification<ParametrageApplication> createSpecification(ParametrageApplicationCriteria criteria) {
        Specification<ParametrageApplication> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ParametrageApplication_.id));
            }
            if (criteria.getNomFichier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomFichier(), ParametrageApplication_.nomFichier));
            }
            if (criteria.getCheminFichier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheminFichier(), ParametrageApplication_.cheminFichier));
            }
        }
        return specification;
    }
}
