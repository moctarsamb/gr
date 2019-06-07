package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.FormatResultat;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the FichierResultat entity. This class is used in FichierResultatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /fichier-resultats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FichierResultatCriteria implements Serializable {
    /**
     * Class for filtering FormatResultat
     */
    public static class FormatResultatFilter extends Filter<FormatResultat> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cheminFichier;

    private FormatResultatFilter format;

    private BooleanFilter avecStatistiques;

    private LongFilter envoiResultatId;

    private LongFilter resultatId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(StringFilter cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public FormatResultatFilter getFormat() {
        return format;
    }

    public void setFormat(FormatResultatFilter format) {
        this.format = format;
    }

    public BooleanFilter getAvecStatistiques() {
        return avecStatistiques;
    }

    public void setAvecStatistiques(BooleanFilter avecStatistiques) {
        this.avecStatistiques = avecStatistiques;
    }

    public LongFilter getEnvoiResultatId() {
        return envoiResultatId;
    }

    public void setEnvoiResultatId(LongFilter envoiResultatId) {
        this.envoiResultatId = envoiResultatId;
    }

    public LongFilter getResultatId() {
        return resultatId;
    }

    public void setResultatId(LongFilter resultatId) {
        this.resultatId = resultatId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FichierResultatCriteria that = (FichierResultatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cheminFichier, that.cheminFichier) &&
            Objects.equals(format, that.format) &&
            Objects.equals(avecStatistiques, that.avecStatistiques) &&
            Objects.equals(envoiResultatId, that.envoiResultatId) &&
            Objects.equals(resultatId, that.resultatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cheminFichier,
        format,
        avecStatistiques,
        envoiResultatId,
        resultatId
        );
    }

    @Override
    public String toString() {
        return "FichierResultatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cheminFichier != null ? "cheminFichier=" + cheminFichier + ", " : "") +
                (format != null ? "format=" + format + ", " : "") +
                (avecStatistiques != null ? "avecStatistiques=" + avecStatistiques + ", " : "") +
                (envoiResultatId != null ? "envoiResultatId=" + envoiResultatId + ", " : "") +
                (resultatId != null ? "resultatId=" + resultatId + ", " : "") +
            "}";
    }

}
