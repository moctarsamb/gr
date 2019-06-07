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
 * Criteria class for the Utilisateur entity. This class is used in UtilisateurResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /utilisateurs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UtilisateurCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter matricule;

    private StringFilter username;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private BooleanFilter estActif;

    private StringFilter userId;

    private LongFilter envoiResultatId;

    private LongFilter notificationId;

    private LongFilter profilAdministreId;

    private LongFilter requisitionId;

    private LongFilter droitAccesId;

    private LongFilter filialeId;

    private LongFilter profilId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMatricule() {
        return matricule;
    }

    public void setMatricule(StringFilter matricule) {
        this.matricule = matricule;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public BooleanFilter getEstActif() {
        return estActif;
    }

    public void setEstActif(BooleanFilter estActif) {
        this.estActif = estActif;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public LongFilter getEnvoiResultatId() {
        return envoiResultatId;
    }

    public void setEnvoiResultatId(LongFilter envoiResultatId) {
        this.envoiResultatId = envoiResultatId;
    }

    public LongFilter getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(LongFilter notificationId) {
        this.notificationId = notificationId;
    }

    public LongFilter getProfilAdministreId() {
        return profilAdministreId;
    }

    public void setProfilAdministreId(LongFilter profilAdministreId) {
        this.profilAdministreId = profilAdministreId;
    }

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }

    public LongFilter getDroitAccesId() {
        return droitAccesId;
    }

    public void setDroitAccesId(LongFilter droitAccesId) {
        this.droitAccesId = droitAccesId;
    }

    public LongFilter getFilialeId() {
        return filialeId;
    }

    public void setFilialeId(LongFilter filialeId) {
        this.filialeId = filialeId;
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
        final UtilisateurCriteria that = (UtilisateurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(username, that.username) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(estActif, that.estActif) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(envoiResultatId, that.envoiResultatId) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(profilAdministreId, that.profilAdministreId) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(droitAccesId, that.droitAccesId) &&
            Objects.equals(filialeId, that.filialeId) &&
            Objects.equals(profilId, that.profilId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        matricule,
        username,
        firstName,
        lastName,
        email,
        estActif,
        userId,
        envoiResultatId,
        notificationId,
        profilAdministreId,
        requisitionId,
        droitAccesId,
        filialeId,
        profilId
        );
    }

    @Override
    public String toString() {
        return "UtilisateurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (estActif != null ? "estActif=" + estActif + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (envoiResultatId != null ? "envoiResultatId=" + envoiResultatId + ", " : "") +
                (notificationId != null ? "notificationId=" + notificationId + ", " : "") +
                (profilAdministreId != null ? "profilAdministreId=" + profilAdministreId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (droitAccesId != null ? "droitAccesId=" + droitAccesId + ", " : "") +
                (filialeId != null ? "filialeId=" + filialeId + ", " : "") +
                (profilId != null ? "profilId=" + profilId + ", " : "") +
            "}";
    }

}
