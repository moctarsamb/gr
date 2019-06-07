package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.StatusEvolution;
import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.EtatResultat;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Resultat entity. This class is used in ResultatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /resultats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultatCriteria implements Serializable {
    /**
     * Class for filtering StatusEvolution
     */
    public static class StatusEvolutionFilter extends Filter<StatusEvolution> {
    }
    /**
     * Class for filtering EtatResultat
     */
    public static class EtatResultatFilter extends Filter<EtatResultat> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StatusEvolutionFilter status;

    private EtatResultatFilter etat;

    private LongFilter champsRechercheId;

    private LongFilter fichierResultatId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StatusEvolutionFilter getStatus() {
        return status;
    }

    public void setStatus(StatusEvolutionFilter status) {
        this.status = status;
    }

    public EtatResultatFilter getEtat() {
        return etat;
    }

    public void setEtat(EtatResultatFilter etat) {
        this.etat = etat;
    }

    public LongFilter getChampsRechercheId() {
        return champsRechercheId;
    }

    public void setChampsRechercheId(LongFilter champsRechercheId) {
        this.champsRechercheId = champsRechercheId;
    }

    public LongFilter getFichierResultatId() {
        return fichierResultatId;
    }

    public void setFichierResultatId(LongFilter fichierResultatId) {
        this.fichierResultatId = fichierResultatId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResultatCriteria that = (ResultatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(etat, that.etat) &&
            Objects.equals(champsRechercheId, that.champsRechercheId) &&
            Objects.equals(fichierResultatId, that.fichierResultatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        etat,
        champsRechercheId,
        fichierResultatId
        );
    }

    @Override
    public String toString() {
        return "ResultatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (etat != null ? "etat=" + etat + ", " : "") +
                (champsRechercheId != null ? "champsRechercheId=" + champsRechercheId + ", " : "") +
                (fichierResultatId != null ? "fichierResultatId=" + fichierResultatId + ", " : "") +
            "}";
    }

}
