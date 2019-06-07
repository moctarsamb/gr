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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.ChampsRecherche;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ChampsRechercheRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ChampsRechercheSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ChampsRechercheCriteria;

/**
 * Service for executing complex queries for ChampsRecherche entities in the database.
 * The main input is a {@link ChampsRechercheCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChampsRecherche} or a {@link Page} of {@link ChampsRecherche} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChampsRechercheQueryService extends QueryService<ChampsRecherche> {

    private final Logger log = LoggerFactory.getLogger(ChampsRechercheQueryService.class);

    private final ChampsRechercheRepository champsRechercheRepository;

    private final ChampsRechercheSearchRepository champsRechercheSearchRepository;

    public ChampsRechercheQueryService(ChampsRechercheRepository champsRechercheRepository, ChampsRechercheSearchRepository champsRechercheSearchRepository) {
        this.champsRechercheRepository = champsRechercheRepository;
        this.champsRechercheSearchRepository = champsRechercheSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ChampsRecherche} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChampsRecherche> findByCriteria(ChampsRechercheCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChampsRecherche> specification = createSpecification(criteria);
        return champsRechercheRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChampsRecherche} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChampsRecherche> findByCriteria(ChampsRechercheCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChampsRecherche> specification = createSpecification(criteria);
        return champsRechercheRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChampsRechercheCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChampsRecherche> specification = createSpecification(criteria);
        return champsRechercheRepository.count(specification);
    }

    /**
     * Function to convert ChampsRechercheCriteria to a {@link Specification}
     */
    private Specification<ChampsRecherche> createSpecification(ChampsRechercheCriteria criteria) {
        Specification<ChampsRecherche> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChampsRecherche_.id));
            }
            if (criteria.getChamps() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChamps(), ChampsRecherche_.champs));
            }
            if (criteria.getDateDebutExtraction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDebutExtraction(), ChampsRecherche_.dateDebutExtraction));
            }
            if (criteria.getDateFinExtraction() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFinExtraction(), ChampsRecherche_.dateFinExtraction));
            }
            if (criteria.getResultatId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultatId(),
                    root -> root.join(ChampsRecherche_.resultat, JoinType.LEFT).get(Resultat_.id)));
            }
            if (criteria.getColonneId() != null) {
                specification = specification.and(buildSpecification(criteria.getColonneId(),
                    root -> root.join(ChampsRecherche_.colonne, JoinType.LEFT).get(Colonne_.id)));
            }
            if (criteria.getEnvironnementId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvironnementId(),
                    root -> root.join(ChampsRecherche_.environnement, JoinType.LEFT).get(Environnement_.id)));
            }
            if (criteria.getFilialeId() != null) {
                specification = specification.and(buildSpecification(criteria.getFilialeId(),
                    root -> root.join(ChampsRecherche_.filiale, JoinType.LEFT).get(Filiale_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(ChampsRecherche_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
            if (criteria.getTypeExtractionId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeExtractionId(),
                    root -> root.join(ChampsRecherche_.typeExtraction, JoinType.LEFT).get(TypeExtraction_.id)));
            }
        }
        return specification;
    }
}
