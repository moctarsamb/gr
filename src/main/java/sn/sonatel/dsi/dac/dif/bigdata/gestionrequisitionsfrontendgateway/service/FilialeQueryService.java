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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Filiale;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FilialeRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FilialeSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FilialeCriteria;

/**
 * Service for executing complex queries for Filiale entities in the database.
 * The main input is a {@link FilialeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Filiale} or a {@link Page} of {@link Filiale} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FilialeQueryService extends QueryService<Filiale> {

    private final Logger log = LoggerFactory.getLogger(FilialeQueryService.class);

    private final FilialeRepository filialeRepository;

    private final FilialeSearchRepository filialeSearchRepository;

    public FilialeQueryService(FilialeRepository filialeRepository, FilialeSearchRepository filialeSearchRepository) {
        this.filialeRepository = filialeRepository;
        this.filialeSearchRepository = filialeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Filiale} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Filiale> findByCriteria(FilialeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Filiale> specification = createSpecification(criteria);
        return filialeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Filiale} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Filiale> findByCriteria(FilialeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Filiale> specification = createSpecification(criteria);
        return filialeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FilialeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Filiale> specification = createSpecification(criteria);
        return filialeRepository.count(specification);
    }

    /**
     * Function to convert FilialeCriteria to a {@link Specification}
     */
    private Specification<Filiale> createSpecification(FilialeCriteria criteria) {
        Specification<Filiale> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Filiale_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Filiale_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Filiale_.libelle));
            }
            if (criteria.getChampsRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getChampsRechercheId(),
                    root -> root.join(Filiale_.champsRecherches, JoinType.LEFT).get(ChampsRecherche_.id)));
            }
            if (criteria.getStructureId() != null) {
                specification = specification.and(buildSpecification(criteria.getStructureId(),
                    root -> root.join(Filiale_.structures, JoinType.LEFT).get(Structure_.id)));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(Filiale_.utilisateurs, JoinType.LEFT).get(Utilisateur_.id)));
            }
            if (criteria.getEnvironnementId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvironnementId(),
                    root -> root.join(Filiale_.environnements, JoinType.LEFT).get(Environnement_.id)));
            }
            if (criteria.getProfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfilId(),
                    root -> root.join(Filiale_.profils, JoinType.LEFT).get(Profil_.id)));
            }
        }
        return specification;
    }
}
