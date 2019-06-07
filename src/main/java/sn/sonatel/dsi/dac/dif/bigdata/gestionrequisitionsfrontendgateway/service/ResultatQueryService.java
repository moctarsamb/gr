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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Resultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.ResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.ResultatSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.ResultatCriteria;

/**
 * Service for executing complex queries for Resultat entities in the database.
 * The main input is a {@link ResultatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Resultat} or a {@link Page} of {@link Resultat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultatQueryService extends QueryService<Resultat> {

    private final Logger log = LoggerFactory.getLogger(ResultatQueryService.class);

    private final ResultatRepository resultatRepository;

    private final ResultatSearchRepository resultatSearchRepository;

    public ResultatQueryService(ResultatRepository resultatRepository, ResultatSearchRepository resultatSearchRepository) {
        this.resultatRepository = resultatRepository;
        this.resultatSearchRepository = resultatSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Resultat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Resultat> findByCriteria(ResultatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resultat> specification = createSpecification(criteria);
        return resultatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Resultat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Resultat> findByCriteria(ResultatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resultat> specification = createSpecification(criteria);
        return resultatRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resultat> specification = createSpecification(criteria);
        return resultatRepository.count(specification);
    }

    /**
     * Function to convert ResultatCriteria to a {@link Specification}
     */
    private Specification<Resultat> createSpecification(ResultatCriteria criteria) {
        Specification<Resultat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Resultat_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Resultat_.status));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), Resultat_.etat));
            }
            if (criteria.getChampsRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getChampsRechercheId(),
                    root -> root.join(Resultat_.champsRecherche, JoinType.LEFT).get(ChampsRecherche_.id)));
            }
            if (criteria.getFichierResultatId() != null) {
                specification = specification.and(buildSpecification(criteria.getFichierResultatId(),
                    root -> root.join(Resultat_.fichierResultats, JoinType.LEFT).get(FichierResultat_.id)));
            }
        }
        return specification;
    }
}
