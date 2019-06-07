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
 * Criteria class for the Serveur entity. This class is used in ServeurResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /serveurs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServeurCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter adresseIp;

    private StringFilter login;

    private StringFilter motDePasse;

    private StringFilter repertoireDepots;

    private StringFilter repertoireResultats;

    private StringFilter repertoireParametres;

    private BooleanFilter estActif;

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

    public StringFilter getAdresseIp() {
        return adresseIp;
    }

    public void setAdresseIp(StringFilter adresseIp) {
        this.adresseIp = adresseIp;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(StringFilter motDePasse) {
        this.motDePasse = motDePasse;
    }

    public StringFilter getRepertoireDepots() {
        return repertoireDepots;
    }

    public void setRepertoireDepots(StringFilter repertoireDepots) {
        this.repertoireDepots = repertoireDepots;
    }

    public StringFilter getRepertoireResultats() {
        return repertoireResultats;
    }

    public void setRepertoireResultats(StringFilter repertoireResultats) {
        this.repertoireResultats = repertoireResultats;
    }

    public StringFilter getRepertoireParametres() {
        return repertoireParametres;
    }

    public void setRepertoireParametres(StringFilter repertoireParametres) {
        this.repertoireParametres = repertoireParametres;
    }

    public BooleanFilter getEstActif() {
        return estActif;
    }

    public void setEstActif(BooleanFilter estActif) {
        this.estActif = estActif;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServeurCriteria that = (ServeurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(adresseIp, that.adresseIp) &&
            Objects.equals(login, that.login) &&
            Objects.equals(motDePasse, that.motDePasse) &&
            Objects.equals(repertoireDepots, that.repertoireDepots) &&
            Objects.equals(repertoireResultats, that.repertoireResultats) &&
            Objects.equals(repertoireParametres, that.repertoireParametres) &&
            Objects.equals(estActif, that.estActif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        adresseIp,
        login,
        motDePasse,
        repertoireDepots,
        repertoireResultats,
        repertoireParametres,
        estActif
        );
    }

    @Override
    public String toString() {
        return "ServeurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (adresseIp != null ? "adresseIp=" + adresseIp + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (motDePasse != null ? "motDePasse=" + motDePasse + ", " : "") +
                (repertoireDepots != null ? "repertoireDepots=" + repertoireDepots + ", " : "") +
                (repertoireResultats != null ? "repertoireResultats=" + repertoireResultats + ", " : "") +
                (repertoireParametres != null ? "repertoireParametres=" + repertoireParametres + ", " : "") +
                (estActif != null ? "estActif=" + estActif + ", " : "") +
            "}";
    }

}
