package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serveur.
 */
@Entity
@Table(name = "serveur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "serveur")
public class Serveur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "adresse_ip", nullable = false, unique = true)
    private String adresseIp;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @NotNull
    @Column(name = "repertoire_depots", nullable = false)
    private String repertoireDepots;

    @NotNull
    @Column(name = "repertoire_resultats", nullable = false)
    private String repertoireResultats;

    @NotNull
    @Column(name = "repertoire_parametres", nullable = false)
    private String repertoireParametres;

    @NotNull
    @Column(name = "est_actif", nullable = false)
    private Boolean estActif;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Serveur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresseIp() {
        return adresseIp;
    }

    public Serveur adresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
        return this;
    }

    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }

    public String getLogin() {
        return login;
    }

    public Serveur login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Serveur motDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
        return this;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRepertoireDepots() {
        return repertoireDepots;
    }

    public Serveur repertoireDepots(String repertoireDepots) {
        this.repertoireDepots = repertoireDepots;
        return this;
    }

    public void setRepertoireDepots(String repertoireDepots) {
        this.repertoireDepots = repertoireDepots;
    }

    public String getRepertoireResultats() {
        return repertoireResultats;
    }

    public Serveur repertoireResultats(String repertoireResultats) {
        this.repertoireResultats = repertoireResultats;
        return this;
    }

    public void setRepertoireResultats(String repertoireResultats) {
        this.repertoireResultats = repertoireResultats;
    }

    public String getRepertoireParametres() {
        return repertoireParametres;
    }

    public Serveur repertoireParametres(String repertoireParametres) {
        this.repertoireParametres = repertoireParametres;
        return this;
    }

    public void setRepertoireParametres(String repertoireParametres) {
        this.repertoireParametres = repertoireParametres;
    }

    public Boolean isEstActif() {
        return estActif;
    }

    public Serveur estActif(Boolean estActif) {
        this.estActif = estActif;
        return this;
    }

    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Serveur serveur = (Serveur) o;
        if (serveur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serveur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Serveur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresseIp='" + getAdresseIp() + "'" +
            ", login='" + getLogin() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            ", repertoireDepots='" + getRepertoireDepots() + "'" +
            ", repertoireResultats='" + getRepertoireResultats() + "'" +
            ", repertoireParametres='" + getRepertoireParametres() + "'" +
            ", estActif='" + isEstActif() + "'" +
            "}";
    }
}
