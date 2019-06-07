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
 * Criteria class for the Filtre entity. This class is used in FiltreResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /filtres?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FiltreCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter typeExtractionId;

    private LongFilter clauseId;

    private LongFilter operateurLogiqueId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTypeExtractionId() {
        return typeExtractionId;
    }

    public void setTypeExtractionId(LongFilter typeExtractionId) {
        this.typeExtractionId = typeExtractionId;
    }

    public LongFilter getClauseId() {
        return clauseId;
    }

    public void setClauseId(LongFilter clauseId) {
        this.clauseId = clauseId;
    }

    public LongFilter getOperateurLogiqueId() {
        return operateurLogiqueId;
    }

    public void setOperateurLogiqueId(LongFilter operateurLogiqueId) {
        this.operateurLogiqueId = operateurLogiqueId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FiltreCriteria that = (FiltreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(typeExtractionId, that.typeExtractionId) &&
            Objects.equals(clauseId, that.clauseId) &&
            Objects.equals(operateurLogiqueId, that.operateurLogiqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        typeExtractionId,
        clauseId,
        operateurLogiqueId
        );
    }

    @Override
    public String toString() {
        return "FiltreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
                (clauseId != null ? "clauseId=" + clauseId + ", " : "") +
                (operateurLogiqueId != null ? "operateurLogiqueId=" + operateurLogiqueId + ", " : "") +
            "}";
    }

}
