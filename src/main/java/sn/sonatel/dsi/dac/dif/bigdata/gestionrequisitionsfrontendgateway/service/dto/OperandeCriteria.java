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
 * Criteria class for the Operande entity. This class is used in OperandeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /operandes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperandeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter colonneId;

    private LongFilter valeurId;

    private LongFilter fonctionId;

    private LongFilter clauseId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getColonneId() {
        return colonneId;
    }

    public void setColonneId(LongFilter colonneId) {
        this.colonneId = colonneId;
    }

    public LongFilter getValeurId() {
        return valeurId;
    }

    public void setValeurId(LongFilter valeurId) {
        this.valeurId = valeurId;
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
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
        final OperandeCriteria that = (OperandeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(colonneId, that.colonneId) &&
            Objects.equals(valeurId, that.valeurId) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(clauseId, that.clauseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        colonneId,
        valeurId,
        fonctionId,
        clauseId
        );
    }

    @Override
    public String toString() {
        return "OperandeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (colonneId != null ? "colonneId=" + colonneId + ", " : "") +
                (valeurId != null ? "valeurId=" + valeurId + ", " : "") +
                (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
                (clauseId != null ? "clauseId=" + clauseId + ", " : "") +
            "}";
    }

}
