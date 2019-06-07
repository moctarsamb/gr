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
 * Criteria class for the Valeur entity. This class is used in ValeurResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /valeurs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ValeurCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter valeur;

    private LongFilter operandeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValeur() {
        return valeur;
    }

    public void setValeur(StringFilter valeur) {
        this.valeur = valeur;
    }

    public LongFilter getOperandeId() {
        return operandeId;
    }

    public void setOperandeId(LongFilter operandeId) {
        this.operandeId = operandeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ValeurCriteria that = (ValeurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valeur, that.valeur) &&
            Objects.equals(operandeId, that.operandeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valeur,
        operandeId
        );
    }

    @Override
    public String toString() {
        return "ValeurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valeur != null ? "valeur=" + valeur + ", " : "") +
                (operandeId != null ? "operandeId=" + operandeId + ", " : "") +
            "}";
    }

}
