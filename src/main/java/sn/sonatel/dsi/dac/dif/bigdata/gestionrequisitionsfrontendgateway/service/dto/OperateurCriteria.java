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
 * Criteria class for the Operateur entity. This class is used in OperateurResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /operateurs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperateurCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter operateur;

    private LongFilter clauseId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOperateur() {
        return operateur;
    }

    public void setOperateur(StringFilter operateur) {
        this.operateur = operateur;
    }

    public LongFilter getClauseId() {
        return clauseId;
    }

    public void setClauseId(LongFilter clauseId) {
        this.clauseId = clauseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OperateurCriteria that = (OperateurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(operateur, that.operateur) &&
            Objects.equals(clauseId, that.clauseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        operateur,
        clauseId
        );
    }

    @Override
    public String toString() {
        return "OperateurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (operateur != null ? "operateur=" + operateur + ", " : "") +
                (clauseId != null ? "clauseId=" + clauseId + ", " : "") +
            "}";
    }

}
