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
 * Criteria class for the Flux entity. This class is used in FluxResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /fluxes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FluxCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private StringFilter description;

    private LongFilter colonneId;

    private LongFilter dimensionId;

    private LongFilter jointureId;

    private LongFilter typeExtractionId;

    private LongFilter baseId;

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

    public LongFilter getColonneId() {
        return colonneId;
    }

    public void setColonneId(LongFilter colonneId) {
        this.colonneId = colonneId;
    }

    public LongFilter getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(LongFilter dimensionId) {
        this.dimensionId = dimensionId;
    }

    public LongFilter getJointureId() {
        return jointureId;
    }

    public void setJointureId(LongFilter jointureId) {
        this.jointureId = jointureId;
    }

    public LongFilter getTypeExtractionId() {
        return typeExtractionId;
    }

    public void setTypeExtractionId(LongFilter typeExtractionId) {
        this.typeExtractionId = typeExtractionId;
    }

    public LongFilter getBaseId() {
        return baseId;
    }

    public void setBaseId(LongFilter baseId) {
        this.baseId = baseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FluxCriteria that = (FluxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(description, that.description) &&
            Objects.equals(colonneId, that.colonneId) &&
            Objects.equals(dimensionId, that.dimensionId) &&
            Objects.equals(jointureId, that.jointureId) &&
            Objects.equals(typeExtractionId, that.typeExtractionId) &&
            Objects.equals(baseId, that.baseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        description,
        colonneId,
        dimensionId,
        jointureId,
        typeExtractionId,
        baseId
        );
    }

    @Override
    public String toString() {
        return "FluxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (colonneId != null ? "colonneId=" + colonneId + ", " : "") +
                (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
                (jointureId != null ? "jointureId=" + jointureId + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
                (baseId != null ? "baseId=" + baseId + ", " : "") +
            "}";
    }

}
