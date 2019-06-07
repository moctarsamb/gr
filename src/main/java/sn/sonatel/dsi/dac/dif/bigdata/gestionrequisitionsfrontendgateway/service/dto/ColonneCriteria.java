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
 * Criteria class for the Colonne entity. This class is used in ColonneResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /colonnes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ColonneCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private StringFilter description;

    private StringFilter alias;

    private LongFilter champsRechercheId;

    private LongFilter monitorId;

    private LongFilter typeExtractionRequeteeId;

    private LongFilter fluxId;

    private LongFilter operandeId;

    private LongFilter profilId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getAlias() {
        return alias;
    }

    public void setAlias(StringFilter alias) {
        this.alias = alias;
    }

    public LongFilter getChampsRechercheId() {
        return champsRechercheId;
    }

    public void setChampsRechercheId(LongFilter champsRechercheId) {
        this.champsRechercheId = champsRechercheId;
    }

    public LongFilter getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(LongFilter monitorId) {
        this.monitorId = monitorId;
    }

    public LongFilter getTypeExtractionRequeteeId() {
        return typeExtractionRequeteeId;
    }

    public void setTypeExtractionRequeteeId(LongFilter typeExtractionRequeteeId) {
        this.typeExtractionRequeteeId = typeExtractionRequeteeId;
    }

    public LongFilter getFluxId() {
        return fluxId;
    }

    public void setFluxId(LongFilter fluxId) {
        this.fluxId = fluxId;
    }

    public LongFilter getOperandeId() {
        return operandeId;
    }

    public void setOperandeId(LongFilter operandeId) {
        this.operandeId = operandeId;
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
        final ColonneCriteria that = (ColonneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(description, that.description) &&
            Objects.equals(alias, that.alias) &&
            Objects.equals(champsRechercheId, that.champsRechercheId) &&
            Objects.equals(monitorId, that.monitorId) &&
            Objects.equals(typeExtractionRequeteeId, that.typeExtractionRequeteeId) &&
            Objects.equals(fluxId, that.fluxId) &&
            Objects.equals(operandeId, that.operandeId) &&
            Objects.equals(profilId, that.profilId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        description,
        alias,
        champsRechercheId,
        monitorId,
        typeExtractionRequeteeId,
        fluxId,
        operandeId,
        profilId
        );
    }

    @Override
    public String toString() {
        return "ColonneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (alias != null ? "alias=" + alias + ", " : "") +
                (champsRechercheId != null ? "champsRechercheId=" + champsRechercheId + ", " : "") +
                (monitorId != null ? "monitorId=" + monitorId + ", " : "") +
                (typeExtractionRequeteeId != null ? "typeExtractionRequeteeId=" + typeExtractionRequeteeId + ", " : "") +
                (fluxId != null ? "fluxId=" + fluxId + ", " : "") +
                (operandeId != null ? "operandeId=" + operandeId + ", " : "") +
                (profilId != null ? "profilId=" + profilId + ", " : "") +
            "}";
    }

}
