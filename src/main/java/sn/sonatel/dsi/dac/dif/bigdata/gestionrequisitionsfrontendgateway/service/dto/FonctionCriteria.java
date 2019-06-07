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
 * Criteria class for the Fonction entity. This class is used in FonctionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /fonctions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FonctionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private LongFilter monitorId;

    private LongFilter operandeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(LongFilter monitorId) {
        this.monitorId = monitorId;
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
        final FonctionCriteria that = (FonctionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(monitorId, that.monitorId) &&
            Objects.equals(operandeId, that.operandeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        monitorId,
        operandeId
        );
    }

    @Override
    public String toString() {
        return "FonctionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (monitorId != null ? "monitorId=" + monitorId + ", " : "") +
                (operandeId != null ? "operandeId=" + operandeId + ", " : "") +
            "}";
    }

}
