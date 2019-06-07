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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Profil;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ProfilRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ProfilSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ProfilCriteria;

/**
 * Service for executing complex queries for Profil entities in the database.
 * The main input is a {@link ProfilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Profil} or a {@link Page} of {@link Profil} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfilQueryService extends QueryService<Profil> {

    private final Logger log = LoggerFactory.getLogger(ProfilQueryService.class);

    private final ProfilRepository profilRepository;

    private final ProfilSearchRepository profilSearchRepository;

    public ProfilQueryService(ProfilRepository profilRepository, ProfilSearchRepository profilSearchRepository) {
        this.profilRepository = profilRepository;
        this.profilSearchRepository = profilSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Profil} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Profil> findByCriteria(ProfilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Profil> specification = createSpecification(criteria);
        return profilRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Profil} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Profil> findByCriteria(ProfilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Profil> specification = createSpecification(criteria);
        return profilRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProfilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Profil> specification = createSpecification(criteria);
        return profilRepository.count(specification);
    }

    /**
     * Function to convert ProfilCriteria to a {@link Specification}
     */
    private Specification<Profil> createSpecification(ProfilCriteria criteria) {
        Specification<Profil> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Profil_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Profil_.libelle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Profil_.description));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(Profil_.utilisateurs, JoinType.LEFT).get(Utilisateur_.id)));
            }
            if (criteria.getColonneId() != null) {
                specification = specification.and(buildSpecification(criteria.getColonneId(),
                    root -> root.join(Profil_.colonnes, JoinType.LEFT).get(Colonne_.id)));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(Profil_.typeExtractions, JoinType.LEFT).get(TypeExtraction_.id)));
            }
            if (criteria.getAdministrateurProfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdministrateurProfilId(),
                    root -> root.join(Profil_.administrateurProfil, JoinType.LEFT).get(Utilisateur_.id)));
            }
            if (criteria.getFilialeId() != null) {
                specification = specification.and(buildSpecification(criteria.getFilialeId(),
                    root -> root.join(Profil_.filiales, JoinType.LEFT).get(Filiale_.id)));
            }
        }
        return specification;
    }
}
