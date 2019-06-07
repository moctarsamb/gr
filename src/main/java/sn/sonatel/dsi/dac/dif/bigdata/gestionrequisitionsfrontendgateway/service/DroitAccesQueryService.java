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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.DroitAcces;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.DroitAccesRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.DroitAccesSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.DroitAccesCriteria;

/**
 * Service for executing complex queries for DroitAcces entities in the database.
 * The main input is a {@link DroitAccesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DroitAcces} or a {@link Page} of {@link DroitAcces} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DroitAccesQueryService extends QueryService<DroitAcces> {

    private final Logger log = LoggerFactory.getLogger(DroitAccesQueryService.class);

    private final DroitAccesRepository droitAccesRepository;

    private final DroitAccesSearchRepository droitAccesSearchRepository;

    public DroitAccesQueryService(DroitAccesRepository droitAccesRepository, DroitAccesSearchRepository droitAccesSearchRepository) {
        this.droitAccesRepository = droitAccesRepository;
        this.droitAccesSearchRepository = droitAccesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DroitAcces} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DroitAcces> findByCriteria(DroitAccesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DroitAcces> specification = createSpecification(criteria);
        return droitAccesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DroitAcces} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DroitAcces> findByCriteria(DroitAccesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DroitAcces> specification = createSpecification(criteria);
        return droitAccesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DroitAccesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DroitAcces> specification = createSpecification(criteria);
        return droitAccesRepository.count(specification);
    }

    /**
     * Function to convert DroitAccesCriteria to a {@link Specification}
     */
    private Specification<DroitAcces> createSpecification(DroitAccesCriteria criteria) {
        Specification<DroitAcces> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DroitAcces_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), DroitAcces_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), DroitAcces_.libelle));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(DroitAcces_.utilisateurs, JoinType.LEFT).get(Utilisateur_.id)));
            }
        }
        return specification;
    }
}
