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
 * Criteria class for the Profil entity. This class is used in ProfilResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /profils?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfilCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private StringFilter description;

    private LongFilter utilisateurId;

    private LongFilter colonneId;

    private LongFilter typeExtractionId;

    private LongFilter administrateurProfilId;

    private LongFilter filialeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(LongFilter utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public LongFilter getColonneId() {
        return colonneId;
    }

    public void setColonneId(LongFilter colonneId) {
        this.colonneId = colonneId;
    }

    public LongFilter getTypeExtractionId() {
        return typeExtractionId;
    }

    public void setTypeExtractionId(LongFilter typeExtractionId) {
        this.typeExtractionId = typeExtractionId;
    }

    public LongFilter getAdministrateurProfilId() {
        return administrateurProfilId;
    }

    public void setAdministrateurProfilId(LongFilter administrateurProfilId) {
        this.administrateurProfilId = administrateurProfilId;
    }

    public LongFilter getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(LongFilter filialeId) {
        this.filialeId = filialeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfilCriteria that = (ProfilCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(description, that.description) &&
            Objects.equals(utilisateurId, that.utilisateurId) &&
            Objects.equals(colonneId, that.colonneId) &&
            Objects.equals(typeExtractionId, that.typeExtractionId) &&
            Objects.equals(administrateurProfilId, that.administrateurProfilId) &&
            Objects.equals(filialeId, that.filialeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        description,
        utilisateurId,
        colonneId,
        typeExtractionId,
        administrateurProfilId,
        filialeId
        );
    }

    @Override
    public String toString() {
        return "ProfilCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (utilisateurId != null ? "utilisateurId=" + utilisateurId + ", " : "") +
                (colonneId != null ? "colonneId=" + colonneId + ", " : "") +
                (typeExtractionId != null ? "typeExtractionId=" + typeExtractionId + ", " : "") +
                (administrateurProfilId != null ? "administrateurProfilId=" + administrateurProfilId + ", " : "") +
                (filialeId != null ? "filialeId=" + filialeId + ", " : "") +
            "}";
    }

}
