package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.TypeMonitor;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Monitor entity. This class is used in MonitorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /monitors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MonitorCriteria implements Serializable {
    /**
     * Class for filtering TypeMonitor
     */
    public static class TypeMonitorFilter extends Filter<TypeMonitor> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TypeMonitorFilter type;

    private LongFilter colonneId;

    private LongFilter fonctionId;

    private LongFilter dimensionId;

    private LongFilter typeExtractionId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TypeMonitorFilter getType() {
        return type;
    }

    public void setType(TypeMonitorFilter type) {
        this.type = type;
    }

    public LongFilter getColonneId() {
        return colonneId;
    }

    public void setColonneId(LongFilter colonneId) {
        this.colonneId = colonneId;
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
    }

    public LongFilter getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(LongFilter dimensionId) {
        this.dimensionId = dimensionId;
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
        final MonitorCriteria that = (MonitorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(colonneId, that.colonneId) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(dimensionId, that.dimensionId) &&
            Objects.equals(typeExtractionId, that.typeExtractionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        colonneId,
        fonctionId,
        dimensionId,
        typeExtractionId
        );
    }

    @Override
    public String toString() {
        return "MonitorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (colonneId != null ? "colonneId=" + colonneId + ", " : "") +
                (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
                (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
            "}";
    }

}
