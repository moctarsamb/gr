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
 * Criteria class for the ParametrageApplication entity. This class is used in ParametrageApplicationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /parametrage-applications?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParametrageApplicationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomFichier;

    private StringFilter cheminFichier;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(StringFilter nomFichier) {
        this.nomFichier = nomFichier;
    }

    public StringFilter getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(StringFilter cheminFichier) {
        this.cheminFichier = cheminFichier;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParametrageApplicationCriteria that = (ParametrageApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomFichier, that.nomFichier) &&
            Objects.equals(cheminFichier, that.cheminFichier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomFichier,
        cheminFichier
        );
    }

    @Override
    public String toString() {
        return "ParametrageApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomFichier != null ? "nomFichier=" + nomFichier + ", " : "") +
                (cheminFichier != null ? "cheminFichier=" + cheminFichier + ", " : "") +
            "}";
    }

}
