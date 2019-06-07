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
 * Criteria class for the OperateurLogique entity. This class is used in OperateurLogiqueResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /operateur-logiques?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperateurLogiqueCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter operateurLogique;

    private LongFilter critereId;

    private LongFilter filtreId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOperateurLogique() {
        return operateurLogique;
    }

    public void setOperateurLogique(StringFilter operateurLogique) {
        this.operateurLogique = operateurLogique;
    }

    public LongFilter getCritereId() {
        return critereId;
    }

    public void setCritereId(LongFilter critereId) {
        this.critereId = critereId;
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
        final OperateurLogiqueCriteria that = (OperateurLogiqueCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(operateurLogique, that.operateurLogique) &&
            Objects.equals(critereId, that.critereId) &&
            Objects.equals(filtreId, that.filtreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        operateurLogique,
        critereId,
        filtreId
        );
    }

    @Override
    public String toString() {
        return "OperateurLogiqueCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (operateurLogique != null ? "operateurLogique=" + operateurLogique + ", " : "") +
                (critereId != null ? "critereId=" + critereId + ", " : "") +
                (filtreId != null ? "filtreId=" + filtreId + ", " : "") +
            "}";
    }

}
