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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.TypeExtraction;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.TypeExtractionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.TypeExtractionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.TypeExtractionCriteria;

/**
 * Service for executing complex queries for TypeExtraction entities in the database.
 * The main input is a {@link TypeExtractionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeExtraction} or a {@link Page} of {@link TypeExtraction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeExtractionQueryService extends QueryService<TypeExtraction> {

    private final Logger log = LoggerFactory.getLogger(TypeExtractionQueryService.class);

    private final TypeExtractionRepository typeExtractionRepository;

    private final TypeExtractionSearchRepository typeExtractionSearchRepository;

    public TypeExtractionQueryService(TypeExtractionRepository typeExtractionRepository, TypeExtractionSearchRepository typeExtractionSearchRepository) {
        this.typeExtractionRepository = typeExtractionRepository;
        this.typeExtractionSearchRepository = typeExtractionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TypeExtraction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeExtraction> findByCriteria(TypeExtractionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeExtraction> specification = createSpecification(criteria);
        return typeExtractionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TypeExtraction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeExtraction> findByCriteria(TypeExtractionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeExtraction> specification = createSpecification(criteria);
        return typeExtractionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeExtractionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeExtraction> specification = createSpecification(criteria);
        return typeExtractionRepository.count(specification);
    }

    /**
     * Function to convert TypeExtractionCriteria to a {@link Specification}
     */
    private Specification<TypeExtraction> createSpecification(TypeExtractionCriteria criteria) {
        Specification<TypeExtraction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypeExtraction_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TypeExtraction_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), TypeExtraction_.libelle));
            }
            if (criteria.getChampsRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getChampsRechercheId(),
                    root -> root.join(TypeExtraction_.champsRecherches, JoinType.LEFT).get(ChampsRecherche_.id)));
            }
            if (criteria.getDimensionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDimensionId(),
                    root -> root.join(TypeExtraction_.dimensions, JoinType.LEFT).get(Dimension_.id)));
            }
            if (criteria.getMonitorId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonitorId(),
                    root -> root.join(TypeExtraction_.monitors, JoinType.LEFT).get(Monitor_.id)));
            }
            if (criteria.getBaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getBaseId(),
                    root -> root.join(TypeExtraction_.base, JoinType.LEFT).get(Base_.id)));
            }
            if (criteria.getFiltreId() != null) {
                specification = specification.and(buildSpecification(criteria.getFiltreId(),
                    root -> root.join(TypeExtraction_.filtre, JoinType.LEFT).get(Filtre_.id)));
            }
            if (criteria.getFluxId() != null) {
                specification = specification.and(buildSpecification(criteria.getFluxId(),
                    root -> root.join(TypeExtraction_.flux, JoinType.LEFT).get(Flux_.id)));
            }
            if (criteria.getColonneRequetableId() != null) {
                specification = specification.and(buildSpecification(criteria.getColonneRequetableId(),
                    root -> root.join(TypeExtraction_.colonneRequetables, JoinType.LEFT).get(Colonne_.id)));
            }
            if (criteria.getProfilId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfilId(),
                    root -> root.join(TypeExtraction_.profils, JoinType.LEFT).get(Profil_.id)));
            }
        }
        return specification;
    }
}
