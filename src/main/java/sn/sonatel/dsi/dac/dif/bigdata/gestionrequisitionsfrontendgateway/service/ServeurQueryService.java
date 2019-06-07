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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Serveur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ServeurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ServeurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ServeurCriteria;

/**
 * Service for executing complex queries for Serveur entities in the database.
 * The main input is a {@link ServeurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Serveur} or a {@link Page} of {@link Serveur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServeurQueryService extends QueryService<Serveur> {

    private final Logger log = LoggerFactory.getLogger(ServeurQueryService.class);

    private final ServeurRepository serveurRepository;

    private final ServeurSearchRepository serveurSearchRepository;

    public ServeurQueryService(ServeurRepository serveurRepository, ServeurSearchRepository serveurSearchRepository) {
        this.serveurRepository = serveurRepository;
        this.serveurSearchRepository = serveurSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Serveur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Serveur> findByCriteria(ServeurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Serveur> specification = createSpecification(criteria);
        return serveurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Serveur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Serveur> findByCriteria(ServeurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Serveur> specification = createSpecification(criteria);
        return serveurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServeurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Serveur> specification = createSpecification(criteria);
        return serveurRepository.count(specification);
    }

    /**
     * Function to convert ServeurCriteria to a {@link Specification}
     */
    private Specification<Serveur> createSpecification(ServeurCriteria criteria) {
        Specification<Serveur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Serveur_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Serveur_.nom));
            }
            if (criteria.getAdresseIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresseIp(), Serveur_.adresseIp));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Serveur_.login));
            }
            if (criteria.getMotDePasse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotDePasse(), Serveur_.motDePasse));
            }
            if (criteria.getRepertoireDepots() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRepertoireDepots(), Serveur_.repertoireDepots));
            }
            if (criteria.getRepertoireResultats() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRepertoireResultats(), Serveur_.repertoireResultats));
            }
            if (criteria.getRepertoireParametres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRepertoireParametres(), Serveur_.repertoireParametres));
            }
            if (criteria.getEstActif() != null) {
                specification = specification.and(buildSpecification(criteria.getEstActif(), Serveur_.estActif));
            }
        }
        return specification;
    }
}
