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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Colonne;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ColonneRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ColonneSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ColonneCriteria;

/**
 * Service for executing complex queries for Colonne entities in the database.
 * The main input is a {@link ColonneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Colonne} or a {@link Page} of {@link Colonne} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ColonneQueryService extends QueryService<Colonne> {

    private final Logger log = LoggerFactory.getLogger(ColonneQueryService.class);

    private final ColonneRepository colonneRepository;

    private final ColonneSearchRepository colonneSearchRepository;

    public ColonneQueryService(ColonneRepository colonneRepository, ColonneSearchRepository colonneSearchRepository) {
        this.colonneRepository = colonneRepository;
        this.colonneSearchRepository = colonneSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Colonne} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Colonne> findByCriteria(ColonneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Colonne> specification = createSpecification(criteria);
        return colonneRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Colonne} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Colonne> findByCriteria(ColonneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Colonne> specification = createSpecification(criteria);
        return colonneRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ColonneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Colonne> specification = createSpecification(criteria);
        return colonneRepository.count(specification);
    }

    /**
     * Function to convert ColonneCriteria to a {@link Specification}
     */
    private Specification<Colonne> createSpecification(ColonneCriteria criteria) {
        Specification<Colonne> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Colonne_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Colonne_.libelle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Colonne_.description));
            }
            if (criteria.getAlias() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlias(), Colonne_.alias));
            }
            if (criteria.getChampsRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getChampsRechercheId(),
                    root -> root.join(Colonne_.champsRecherches, JoinType.LEFT).get(ChampsRecherche_.id)));
            }
            if (criteria.getMonitorId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonitorId(),
                    root -> root.join(Colonne_.monitors, JoinType.LEFT).get(Monitor_.id)));
            }
            if (criteria.getTypeExtractionRequeteeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionRequeteeId(),
                    root -> root.join(Colonne_.typeExtractionRequetees, JoinType.LEFT).get(TypeExtraction_.id)));
            }
            if (criteria.getFluxId() != null) {
                specification = specification.and(buildSpecification(criteria.getFluxId(),
                    root -> root.join(Colonne_.flux, JoinType.LEFT).get(Flux_.id)));
            }
            if (criteria.getOperandeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOperandeId(),
                    root -> root.join(Colonne_.operandes, JoinType.LEFT).get(Operande_.id)));
            }
            if (criteria.getProfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfilId(),
                    root -> root.join(Colonne_.profils, JoinType.LEFT).get(Profil_.id)));
            }
        }
        return specification;
    }
}
