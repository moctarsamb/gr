package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the ChampsRecherche entity. This class is used in ChampsRechercheResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /champs-recherches?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChampsRechercheCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter champs;

    private LocalDateFilter dateDebutExtraction;

    private LocalDateFilter dateFinExtraction;

    private LongFilter resultatId;

    private LongFilter colonneId;

    private LongFilter environnementId;

    private LongFilter filialeId;

    private LongFilter requisitionId;

    private LongFilter typeExtractionId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getChamps() {
        return champs;
    }

    public void setChamps(StringFilter champs) {
        this.champs = champs;
    }

    public LocalDateFilter getDateDebutExtraction() {
        return dateDebutExtraction;
    }

    public void setDateDebutExtraction(LocalDateFilter dateDebutExtraction) {
        this.dateDebutExtraction = dateDebutExtraction;
    }

    public LocalDateFilter getDateFinExtraction() {
        return dateFinExtraction;
    }

    public void setDateFinExtraction(LocalDateFilter dateFinExtraction) {
        this.dateFinExtraction = dateFinExtraction;
    }

    public LongFilter getResultatId() {
        return resultatId;
    }

    public void setResultatId(LongFilter resultatId) {
        this.resultatId = resultatId;
    }

    public LongFilter getColonneId() {
        return colonneId;
    }

    public void setColonneId(LongFilter colonneId) {
        this.colonneId = colonneId;
    }

    public LongFilter getEnvironnementId() {
        return environnementId;
    }

    public void setEnvironnementId(LongFilter environnementId) {
        this.environnementId = environnementId;
    }

    public LongFilter getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(LongFilter filialeId) {
        this.filialeId = filialeId;
    }

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }

    public LongFilter getTypeExtractionId() {
        return typeExtractionId;
    }

    public void setTypeExtractionId(LongFilter typeExtractionId) {
        this.typeExtractionId = typeExtractionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChampsRechercheCriteria that = (ChampsRechercheCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(champs, that.champs) &&
            Objects.equals(dateDebutExtraction, that.dateDebutExtraction) &&
            Objects.equals(dateFinExtraction, that.dateFinExtraction) &&
            Objects.equals(resultatId, that.resultatId) &&
            Objects.equals(colonneId, that.colonneId) &&
            Objects.equals(environnementId, that.environnementId) &&
            Objects.equals(filialeId, that.filialeId) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(typeExtractionId, that.typeExtractionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        champs,
        dateDebutExtraction,
        dateFinExtraction,
        resultatId,
        colonneId,
        environnementId,
        filialeId,
        requisitionId,
        typeExtractionId
        );
    }

    @Override
    public String toString() {
        return "ChampsRechercheCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (champs != null ? "champs=" + champs + ", " : "") +
                (dateDebutExtraction != null ? "dateDebutExtraction=" + dateDebutExtraction + ", " : "") +
                (dateFinExtraction != null ? "dateFinExtraction=" + dateFinExtraction + ", " : "") +
                (resultatId != null ? "resultatId=" + resultatId + ", " : "") +
                (colonneId != null ? "colonneId=" + colonneId + ", " : "") +
                (environnementId != null ? "environnementId=" + environnementId + ", " : "") +
                (filialeId != null ? "filialeId=" + filialeId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
            "}";
    }

}
