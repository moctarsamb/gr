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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.Requisition;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.*; // for static metamodels
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.RequisitionRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.repository.search.RequisitionSearchRepository;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto.RequisitionCriteria;

/**
 * Service for executing complex queries for Requisition entities in the database.
 * The main input is a {@link RequisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Requisition} or a {@link Page} of {@link Requisition} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequisitionQueryService extends QueryService<Requisition> {

    private final Logger log = LoggerFactory.getLogger(RequisitionQueryService.class);

    private final RequisitionRepository requisitionRepository;

    private final RequisitionSearchRepository requisitionSearchRepository;

    public RequisitionQueryService(RequisitionRepository requisitionRepository, RequisitionSearchRepository requisitionSearchRepository) {
        this.requisitionRepository = requisitionRepository;
        this.requisitionSearchRepository = requisitionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Requisition} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Requisition> findByCriteria(RequisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Requisition} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Requisition> findByCriteria(RequisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.count(specification);
    }

    /**
     * Function to convert RequisitionCriteria to a {@link Specification}
     */
    private Specification<Requisition> createSpecification(RequisitionCriteria criteria) {
        Specification<Requisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Requisition_.id));
            }
            if (criteria.getNumeroArriveeDemande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroArriveeDemande(), Requisition_.numeroArriveeDemande));
            }
            if (criteria.getNumeroPv() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroPv(), Requisition_.numeroPv));
            }
            if (criteria.getDateSaisiePv() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSaisiePv(), Requisition_.dateSaisiePv));
            }
            if (criteria.getDateArriveeDemande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateArriveeDemande(), Requisition_.dateArriveeDemande));
            }
            if (criteria.getDateSaisieDemande() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateSaisieDemande(), Requisition_.dateSaisieDemande));
            }
            if (criteria.getEnvoiResultatAutomatique() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvoiResultatAutomatique(), Requisition_.envoiResultatAutomatique));
            }
            if (criteria.getDateReponse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReponse(), Requisition_.dateReponse));
            }
            if (criteria.getDateRenvoieResultat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRenvoieResultat(), Requisition_.dateRenvoieResultat));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Requisition_.status));
            }
            if (criteria.getEtat() != null) {
                specification = specification.and(buildSpecification(criteria.getEtat(), Requisition_.etat));
            }
            if (criteria.getChampsRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getChampsRechercheId(),
                    root -> root.join(Requisition_.champsRecherches, JoinType.LEFT).get(ChampsRecherche_.id)));
            }
            if (criteria.getProvenanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getProvenanceId(),
                    root -> root.join(Requisition_.provenance, JoinType.LEFT).get(Provenance_.id)));
            }
            if (criteria.getStructureId() != null) {
                specification = specification.and(buildSpecification(criteria.getStructureId(),
                    root -> root.join(Requisition_.structure, JoinType.LEFT).get(Structure_.id)));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(Requisition_.utilisateur, JoinType.LEFT).get(Utilisateur_.id)));
            }
        }
        return specification;
    }
}
