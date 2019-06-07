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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.FichierResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.FichierResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.FichierResultatSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.FichierResultatCriteria;

/**
 * Service for executing complex queries for FichierResultat entities in the database.
 * The main input is a {@link FichierResultatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FichierResultat} or a {@link Page} of {@link FichierResultat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FichierResultatQueryService extends QueryService<FichierResultat> {

    private final Logger log = LoggerFactory.getLogger(FichierResultatQueryService.class);

    private final FichierResultatRepository fichierResultatRepository;

    private final FichierResultatSearchRepository fichierResultatSearchRepository;

    public FichierResultatQueryService(FichierResultatRepository fichierResultatRepository, FichierResultatSearchRepository fichierResultatSearchRepository) {
        this.fichierResultatRepository = fichierResultatRepository;
        this.fichierResultatSearchRepository = fichierResultatSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FichierResultat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FichierResultat> findByCriteria(FichierResultatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FichierResultat> specification = createSpecification(criteria);
        return fichierResultatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FichierResultat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FichierResultat> findByCriteria(FichierResultatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FichierResultat> specification = createSpecification(criteria);
        return fichierResultatRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FichierResultatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FichierResultat> specification = createSpecification(criteria);
        return fichierResultatRepository.count(specification);
    }

    /**
     * Function to convert FichierResultatCriteria to a {@link Specification}
     */
    private Specification<FichierResultat> createSpecification(FichierResultatCriteria criteria) {
        Specification<FichierResultat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FichierResultat_.id));
            }
            if (criteria.getCheminFichier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheminFichier(), FichierResultat_.cheminFichier));
            }
            if (criteria.getFormat() != null) {
                specification = specification.and(buildSpecification(criteria.getFormat(), FichierResultat_.format));
            }
            if (criteria.getAvecStatistiques() != null) {
                specification = specification.and(buildSpecification(criteria.getAvecStatistiques(), FichierResultat_.avecStatistiques));
            }
            if (criteria.getEnvoiResultatId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvoiResultatId(),
                    root -> root.join(FichierResultat_.envoiResultats, JoinType.LEFT).get(EnvoiResultat_.id)));
            }
            if (criteria.getResultatId() != null) {
                specification = specification.and(buildSpecification(criteria.getResultatId(),
                    root -> root.join(FichierResultat_.resultat, JoinType.LEFT).get(Resultat_.id)));
            }
        }
        return specification;
    }
}
