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
 * Criteria class for the Base entity. This class is used in BaseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /bases?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BaseCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private StringFilter description;

    private LongFilter dimensionId;

    private LongFilter fluxId;

    private LongFilter typeExtractionId;

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

    public LongFilter getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(LongFilter dimensionId) {
        this.dimensionId = dimensionId;
    }

    public LongFilter getFluxId() {
        return fluxId;
    }

    public void setFluxId(LongFilter fluxId) {
        this.fluxId = fluxId;
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
        final BaseCriteria that = (BaseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dimensionId, that.dimensionId) &&
            Objects.equals(fluxId, that.fluxId) &&
            Objects.equals(typeExtractionId, that.typeExtractionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        description,
        dimensionId,
        fluxId,
        typeExtractionId
        );
    }

    @Override
    public String toString() {
        return "BaseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
                (fluxId != null ? "fluxId=" + fluxId + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
            "}";
    }

}
