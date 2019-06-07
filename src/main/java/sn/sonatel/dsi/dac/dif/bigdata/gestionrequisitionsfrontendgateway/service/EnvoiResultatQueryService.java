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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.EnvoiResultat;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.EnvoiResultatRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.EnvoiResultatSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.EnvoiResultatCriteria;

/**
 * Service for executing complex queries for EnvoiResultat entities in the database.
 * The main input is a {@link EnvoiResultatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnvoiResultat} or a {@link Page} of {@link EnvoiResultat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnvoiResultatQueryService extends QueryService<EnvoiResultat> {

    private final Logger log = LoggerFactory.getLogger(EnvoiResultatQueryService.class);

    private final EnvoiResultatRepository envoiResultatRepository;

    private final EnvoiResultatSearchRepository envoiResultatSearchRepository;

    public EnvoiResultatQueryService(EnvoiResultatRepository envoiResultatRepository, EnvoiResultatSearchRepository envoiResultatSearchRepository) {
        this.envoiResultatRepository = envoiResultatRepository;
        this.envoiResultatSearchRepository = envoiResultatSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EnvoiResultat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnvoiResultat> findByCriteria(EnvoiResultatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnvoiResultat> specification = createSpecification(criteria);
        return envoiResultatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EnvoiResultat} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnvoiResultat> findByCriteria(EnvoiResultatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnvoiResultat> specification = createSpecification(criteria);
        return envoiResultatRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnvoiResultatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnvoiResultat> specification = createSpecification(criteria);
        return envoiResultatRepository.count(specification);
    }

    /**
     * Function to convert EnvoiResultatCriteria to a {@link Specification}
     */
    private Specification<EnvoiResultat> createSpecification(EnvoiResultatCriteria criteria) {
        Specification<EnvoiResultat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EnvoiResultat_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), EnvoiResultat_.email));
            }
            if (criteria.getDateEnvoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEnvoi(), EnvoiResultat_.dateEnvoi));
            }
            if (criteria.getFichierResultatId() != null) {
                specification = specification.and(buildSpecification(criteria.getFichierResultatId(),
                    root -> root.join(EnvoiResultat_.fichierResultat, JoinType.LEFT).get(FichierResultat_.id)));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(EnvoiResultat_.utilisateur, JoinType.LEFT).get(Utilisateur_.id)));
            }
        }
        return specification;
    }
}
