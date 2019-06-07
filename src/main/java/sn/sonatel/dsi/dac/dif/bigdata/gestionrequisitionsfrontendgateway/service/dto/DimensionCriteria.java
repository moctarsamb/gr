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
 * Criteria class for the Dimension entity. This class is used in DimensionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /dimensions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DimensionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter monitorId;

    private LongFilter baseId;

    private LongFilter fluxId;

    private LongFilter typeExtractionId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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
        final DimensionCriteria that = (DimensionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(monitorId, that.monitorId) &&
            Objects.equals(baseId, that.baseId) &&
            Objects.equals(fluxId, that.fluxId) &&
            Objects.equals(typeExtractionId, that.typeExtractionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        monitorId,
        baseId,
        fluxId,
        typeExtractionId
        );
    }

    @Override
    public String toString() {
        return "DimensionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (monitorId != null ? "monitorId=" + monitorId + ", " : "") +
                (baseId != null ? "baseId=" + baseId + ", " : "") +
                (fluxId != null ? "fluxId=" + fluxId + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
            "}";
    }

}
