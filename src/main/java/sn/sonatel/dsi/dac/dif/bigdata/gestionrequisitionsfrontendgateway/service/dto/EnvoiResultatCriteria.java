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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the EnvoiResultat entity. This class is used in EnvoiResultatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /envoi-resultats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnvoiResultatCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private InstantFilter dateEnvoi;

    private LongFilter fichierResultatId;

    private LongFilter utilisateurId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public InstantFilter getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(InstantFilter dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public LongFilter getFichierResultatId() {
        return fichierResultatId;
    }

    public void setFichierResultatId(LongFilter fichierResultatId) {
        this.fichierResultatId = fichierResultatId;
    }

    public LongFilter getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(LongFilter utilisateurId) {
        this.utilisateurId = utilisateurId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnvoiResultatCriteria that = (EnvoiResultatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(dateEnvoi, that.dateEnvoi) &&
            Objects.equals(fichierResultatId, that.fichierResultatId) &&
            Objects.equals(utilisateurId, that.utilisateurId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        email,
        dateEnvoi,
        fichierResultatId,
        utilisateurId
        );
    }

    @Override
    public String toString() {
        return "EnvoiResultatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (dateEnvoi != null ? "dateEnvoi=" + dateEnvoi + ", " : "") +
                (fichierResultatId != null ? "fichierResultatId=" + fichierResultatId + ", " : "") +
                (utilisateurId != null ? "utilisateurId=" + utilisateurId + ", " : "") +
            "}";
    }

}
