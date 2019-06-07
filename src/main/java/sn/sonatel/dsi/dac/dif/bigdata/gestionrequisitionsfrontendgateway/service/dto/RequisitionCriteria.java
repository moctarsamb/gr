package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.StatusEvolution;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.EtatRequisition;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Requisition entity. This class is used in RequisitionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /requisitions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequisitionCriteria implements Serializable {
    /**
     * Class for filtering StatusEvolution
     */
    public static class StatusEvolutionFilter extends Filter<StatusEvolution> {
    }
    /**
     * Class for filtering EtatRequisition
     */
    public static class EtatRequisitionFilter extends Filter<EtatRequisition> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numeroArriveeDemande;

    private IntegerFilter numeroPv;

    private LocalDateFilter dateSaisiePv;

    private InstantFilter dateArriveeDemande;

    private InstantFilter dateSaisieDemande;

    private BooleanFilter envoiResultatAutomatique;

    private InstantFilter dateReponse;

    private InstantFilter dateRenvoieResultat;

    private StatusEvolutionFilter status;

    private EtatRequisitionFilter etat;

    private LongFilter champsRechercheId;

    private LongFilter provenanceId;

    private LongFilter structureId;

    private LongFilter utilisateurId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumeroArriveeDemande() {
        return numeroArriveeDemande;
    }

    public void setNumeroArriveeDemande(IntegerFilter numeroArriveeDemande) {
        this.numeroArriveeDemande = numeroArriveeDemande;
    }

    public IntegerFilter getNumeroPv() {
        return numeroPv;
    }

    public void setNumeroPv(IntegerFilter numeroPv) {
        this.numeroPv = numeroPv;
    }

    public LocalDateFilter getDateSaisiePv() {
        return dateSaisiePv;
    }

    public void setDateSaisiePv(LocalDateFilter dateSaisiePv) {
        this.dateSaisiePv = dateSaisiePv;
    }

    public InstantFilter getDateArriveeDemande() {
        return dateArriveeDemande;
    }

    public void setDateArriveeDemande(InstantFilter dateArriveeDemande) {
        this.dateArriveeDemande = dateArriveeDemande;
    }

    public InstantFilter getDateSaisieDemande() {
        return dateSaisieDemande;
    }

    public void setDateSaisieDemande(InstantFilter dateSaisieDemande) {
        this.dateSaisieDemande = dateSaisieDemande;
    }

    public BooleanFilter getEnvoiResultatAutomatique() {
        return envoiResultatAutomatique;
    }

    public void setEnvoiResultatAutomatique(BooleanFilter envoiResultatAutomatique) {
        this.envoiResultatAutomatique = envoiResultatAutomatique;
    }

    public InstantFilter getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(InstantFilter dateReponse) {
        this.dateReponse = dateReponse;
    }

    public InstantFilter getDateRenvoieResultat() {
        return dateRenvoieResultat;
    }

    public void setDateRenvoieResultat(InstantFilter dateRenvoieResultat) {
        this.dateRenvoieResultat = dateRenvoieResultat;
    }

    public StatusEvolutionFilter getStatus() {
        return status;
    }

    public void setStatus(StatusEvolutionFilter status) {
        this.status = status;
    }

    public EtatRequisitionFilter getEtat() {
        return etat;
    }

    public void setEtat(EtatRequisitionFilter etat) {
        this.etat = etat;
    }

    public LongFilter getChampsRechercheId() {
        return champsRechercheId;
    }

    public void setChampsRechercheId(LongFilter champsRechercheId) {
        this.champsRechercheId = champsRechercheId;
    }

    public LongFilter getProvenanceId() {
        return provenanceId;
    }

    public void setProvenanceId(LongFilter provenanceId) {
        this.provenanceId = provenanceId;
    }

    public LongFilter getStructureId() {
        return structureId;
    }

    public void setStructureId(LongFilter structureId) {
        this.structureId = structureId;
    }

    public LongFilter getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(LongFilter utilisateurId) {
        this.utilisateurId = utilisateurId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequisitionCriteria that = (RequisitionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numeroArriveeDemande, that.numeroArriveeDemande) &&
            Objects.equals(numeroPv, that.numeroPv) &&
            Objects.equals(dateSaisiePv, that.dateSaisiePv) &&
            Objects.equals(dateArriveeDemande, that.dateArriveeDemande) &&
            Objects.equals(dateSaisieDemande, that.dateSaisieDemande) &&
            Objects.equals(envoiResultatAutomatique, that.envoiResultatAutomatique) &&
            Objects.equals(dateReponse, that.dateReponse) &&
            Objects.equals(dateRenvoieResultat, that.dateRenvoieResultat) &&
            Objects.equals(status, that.status) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(champsRechercheId, that.champsRechercheId) &&
            Objects.equals(provenanceId, that.provenanceId) &&
            Objects.equals(structureId, that.structureId) &&
            Objects.equals(utilisateurId, that.utilisateurId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numeroArriveeDemande,
        numeroPv,
        dateSaisiePv,
        dateArriveeDemande,
        dateSaisieDemande,
        envoiResultatAutomatique,
        dateReponse,
        dateRenvoieResultat,
        status,
        etat,
        champsRechercheId,
        provenanceId,
        structureId,
        utilisateurId
        );
    }

    @Override
    public String toString() {
        return "RequisitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numeroArriveeDemande != null ? "numeroArriveeDemande=" + numeroArriveeDemande + ", " : "") +
                (numeroPv != null ? "numeroPv=" + numeroPv + ", " : "") +
                (dateSaisiePv != null ? "dateSaisiePv=" + dateSaisiePv + ", " : "") +
                (dateArriveeDemande != null ? "dateArriveeDemande=" + dateArriveeDemande + ", " : "") +
                (dateSaisieDemande != null ? "dateSaisieDemande=" + dateSaisieDemande + ", " : "") +
                (envoiResultatAutomatique != null ? "envoiResultatAutomatique=" + envoiResultatAutomatique + ", " : "") +
                (dateReponse != null ? "dateReponse=" + dateReponse + ", " : "") +
                (dateRenvoieResultat != null ? "dateRenvoieResultat=" + dateRenvoieResultat + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (champsRechercheId != null ? "champsRechercheId=" + champsRechercheId + ", " : "") +
                (provenanceId != null ? "provenanceId=" + provenanceId + ", " : "") +
                (structureId != null ? "structureId=" + structureId + ", " : "") +
                (utilisateurId != null ? "utilisateurId=" + utilisateurId + ", " : "") +
            "}";
    }

}
