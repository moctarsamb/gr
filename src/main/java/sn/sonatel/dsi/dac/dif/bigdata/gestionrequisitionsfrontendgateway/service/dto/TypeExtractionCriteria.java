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

/**
 * Criteria class for the TypeExtraction entity. This class is used in TypeExtractionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /type-extractions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeExtractionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter libelle;

    private LongFilter champsRechercheId;

    private LongFilter dimensionId;

    private LongFilter monitorId;

    private LongFilter baseId;

    private LongFilter filtreId;

    private LongFilter fluxId;

    private LongFilter colonneRequetableId;

    private LongFilter profilId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public LongFilter getChampsRechercheId() {
        return champsRechercheId;
    }

    public void setChampsRechercheId(LongFilter champsRechercheId) {
        this.champsRechercheId = champsRechercheId;
    }

    public LongFilter getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(LongFilter dimensionId) {
        this.dimensionId = dimensionId;
    }

    public LongFilter getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(LongFilter monitorId) {
        this.monitorId = monitorId;
    }

    public LongFilter getBaseId() {
        return baseId;
    }

    public void setBaseId(LongFilter baseId) {
        this.baseId = baseId;
    }

    public LongFilter getFiltreId() {
        return filtreId;
    }

    public void setFiltreId(LongFilter filtreId) {
        this.filtreId = filtreId;
    }

    public LongFilter getFluxId() {
        return fluxId;
    }

    public void setFluxId(LongFilter fluxId) {
        this.fluxId = fluxId;
    }

    public LongFilter getColonneRequetableId() {
        return colonneRequetableId;
    }

    public void setColonneRequetableId(LongFilter colonneRequetableId) {
        this.colonneRequetableId = colonneRequetableId;
    }

    public LongFilter getProfilId() {
        return profilId;
    }

    public void setProfilId(LongFilter profilId) {
        this.profilId = profilId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeExtractionCriteria that = (TypeExtractionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(champsRechercheId, that.champsRechercheId) &&
            Objects.equals(dimensionId, that.dimensionId) &&
            Objects.equals(monitorId, that.monitorId) &&
            Objects.equals(baseId, that.baseId) &&
            Objects.equals(filtreId, that.filtreId) &&
            Objects.equals(fluxId, that.fluxId) &&
            Objects.equals(colonneRequetableId, that.colonneRequetableId) &&
            Objects.equals(profilId, that.profilId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        libelle,
        champsRechercheId,
        dimensionId,
        monitorId,
        baseId,
        filtreId,
        fluxId,
        colonneRequetableId,
        profilId
        );
    }

    @Override
    public String toString() {
        return "TypeExtractionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (champsRechercheId != null ? "champsRechercheId=" + champsRechercheId + ", " : "") +
                (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
                (monitorId != null ? "monitorId=" + monitorId + ", " : "") +
                (baseId != null ? "baseId=" + baseId + ", " : "") +
                (filtreId != null ? "filtreId=" + filtreId + ", " : "") +
                (fluxId != null ? "fluxId=" + fluxId + ", " : "") +
                (colonneRequetableId != null ? "colonneRequetableId=" + colonneRequetableId + ", " : "") +
                (profilId != null ? "profilId=" + profilId + ", " : "") +
            "}";
    }

}
