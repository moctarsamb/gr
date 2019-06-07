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
 * Criteria class for the Clause entity. This class is used in ClauseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /clauses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClauseCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prefixe;

    private StringFilter suffixe;

    private LongFilter critereId;

    private LongFilter operandeId;

    private LongFilter operateurId;

    private LongFilter filtreId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPrefixe() {
        return prefixe;
    }

    public void setPrefixe(StringFilter prefixe) {
        this.prefixe = prefixe;
    }

    public StringFilter getSuffixe() {
        return suffixe;
    }

    public void setSuffixe(StringFilter suffixe) {
        this.suffixe = suffixe;
    }

    public LongFilter getCritereId() {
        return critereId;
    }

    public void setCritereId(LongFilter critereId) {
        this.critereId = critereId;
    }

    public LongFilter getOperandeId() {
        return operandeId;
    }

    public void setOperandeId(LongFilter operandeId) {
        this.operandeId = operandeId;
    }

    public LongFilter getOperateurId() {
        return operateurId;
    }

    public void setOperateurId(LongFilter operateurId) {
        this.operateurId = operateurId;
    }

    public LongFilter getFiltreId() {
        return filtreId;
    }

    public void setFiltreId(LongFilter filtreId) {
        this.filtreId = filtreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClauseCriteria that = (ClauseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(prefixe, that.prefixe) &&
            Objects.equals(suffixe, that.suffixe) &&
            Objects.equals(critereId, that.critereId) &&
            Objects.equals(operandeId, that.operandeId) &&
            Objects.equals(operateurId, that.operateurId) &&
            Objects.equals(filtreId, that.filtreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        prefixe,
        suffixe,
        critereId,
        operandeId,
        operateurId,
        filtreId
        );
    }

    @Override
    public String toString() {
        return "ClauseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (prefixe != null ? "prefixe=" + prefixe + ", " : "") +
                (suffixe != null ? "suffixe=" + suffixe + ", " : "") +
                (critereId != null ? "critereId=" + critereId + ", " : "") +
                (operandeId != null ? "operandeId=" + operandeId + ", " : "") +
                (operateurId != null ? "operateurId=" + operateurId + ", " : "") +
                (filtreId != null ? "filtreId=" + filtreId + ", " : "") +
            "}";
    }

}
