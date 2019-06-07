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
 * Criteria class for the Filiale entity. This class is used in FilialeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /filiales?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FilialeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter libelle;

    private LongFilter champsRechercheId;

    private LongFilter structureId;

    private LongFilter utilisateurId;

    private LongFilter environnementId;

    private LongFilter profilId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public LongFilter getChampsRechercheId() {
        return champsRechercheId;
    }

    public void setChampsRechercheId(LongFilter champsRechercheId) {
        this.champsRechercheId = champsRechercheId;
    }

    public LongFilter getStructureId() {
        return structureId;
    }

    public void setStructureId(LongFilter structureId) {
        this.structureId = structureId;
    }

    public LongFilter getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(LongFilter utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public LongFilter getEnvironnementId() {
        return environnementId;
    }

    public void setEnvironnementId(LongFilter environnementId) {
        this.environnementId = environnementId;
    }

    public LongFilter getProfilId() {
        return profilId;
    }

    public void setProfilId(LongFilter profilId) {
        this.profilId = profilId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FilialeCriteria that = (FilialeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(champsRechercheId, that.champsRechercheId) &&
            Objects.equals(structureId, that.structureId) &&
            Objects.equals(utilisateurId, that.utilisateurId) &&
            Objects.equals(environnementId, that.environnementId) &&
            Objects.equals(profilId, that.profilId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        libelle,
        champsRechercheId,
        structureId,
        utilisateurId,
        environnementId,
        profilId
        );
    }

    @Override
    public String toString() {
        return "FilialeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (champsRechercheId != null ? "champsRechercheId=" + champsRechercheId + ", " : "") +
                (structureId != null ? "structureId=" + structureId + ", " : "") +
                (utilisateurId != null ? "utilisateurId=" + utilisateurId + ", " : "") +
                (environnementId != null ? "environnementId=" + environnementId + ", " : "") +
                (profilId != null ? "profilId=" + profilId + ", " : "") +
            "}";
    }

}
