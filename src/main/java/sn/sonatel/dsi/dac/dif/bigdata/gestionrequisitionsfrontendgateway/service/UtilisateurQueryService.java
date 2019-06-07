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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Utilisateur;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.UtilisateurRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.UtilisateurSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.UtilisateurCriteria;

/**
 * Service for executing complex queries for Utilisateur entities in the database.
 * The main input is a {@link UtilisateurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Utilisateur} or a {@link Page} of {@link Utilisateur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UtilisateurQueryService extends QueryService<Utilisateur> {

    private final Logger log = LoggerFactory.getLogger(UtilisateurQueryService.class);

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurSearchRepository utilisateurSearchRepository;

    public UtilisateurQueryService(UtilisateurRepository utilisateurRepository, UtilisateurSearchRepository utilisateurSearchRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurSearchRepository = utilisateurSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Utilisateur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Utilisateur> findByCriteria(UtilisateurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Utilisateur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Utilisateur> findByCriteria(UtilisateurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UtilisateurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.count(specification);
    }

    /**
     * Function to convert UtilisateurCriteria to a {@link Specification}
     */
    private Specification<Utilisateur> createSpecification(UtilisateurCriteria criteria) {
        Specification<Utilisateur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Utilisateur_.id));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Utilisateur_.matricule));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Utilisateur_.username));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Utilisateur_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Utilisateur_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Utilisateur_.email));
            }
            if (criteria.getEstActif() != null) {
                specification = specification.and(buildSpecification(criteria.getEstActif(), Utilisateur_.estActif));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Utilisateur_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getEnvoiResultatId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvoiResultatId(),
                    root -> root.join(Utilisateur_.envoiResultats, JoinType.LEFT).get(EnvoiResultat_.id)));
            }
            if (criteria.getNotificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificationId(),
                    root -> root.join(Utilisateur_.notifications, JoinType.LEFT).get(Notification_.id)));
            }
            if (criteria.getProfilAdministreId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfilAdministreId(),
                    root -> root.join(Utilisateur_.profilAdministres, JoinType.LEFT).get(Profil_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(Utilisateur_.requisitions, JoinType.LEFT).get(Requisition_.id)));
            }
            if (criteria.getDroitAccesId() != null) {
                specification = specification.and(buildSpecification(criteria.getDroitAccesId(),
                    root -> root.join(Utilisateur_.droitAcces, JoinType.LEFT).get(DroitAcces_.id)));
            }
            if (criteria.getFilialeId() != null) {
                specification = specification.and(buildSpecification(criteria.getFilialeId(),
                    root -> root.join(Utilisateur_.filiale, JoinType.LEFT).get(Filiale_.id)));
            }
            if (criteria.getProfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfilId(),
                    root -> root.join(Utilisateur_.profil, JoinType.LEFT).get(Profil_.id)));
            }
        }
        return specification;
    }
}
