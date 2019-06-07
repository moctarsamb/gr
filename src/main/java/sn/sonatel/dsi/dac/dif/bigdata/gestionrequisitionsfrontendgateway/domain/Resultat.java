package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.StatusEvolution;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.EtatResultat;

/**
 * A Resultat.
 */
@Entity
@Table(name = "resultat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resultat")
public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEvolution status;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatResultat etat;

    @OneToOne
    @JoinColumn(unique = true)
    private ChampsRecherche champsRecherche;

    @OneToMany(mappedBy = "resultat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FichierResultat> fichierResultats = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEvolution getStatus() {
        return status;
    }

    public Resultat status(StatusEvolution status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEvolution status) {
        this.status = status;
    }

    public EtatResultat getEtat() {
        return etat;
    }

    public Resultat etat(EtatResultat etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatResultat etat) {
        this.etat = etat;
    }

    public ChampsRecherche getChampsRecherche() {
        return champsRecherche;
    }

    public Resultat champsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherche = champsRecherche;
        return this;
    }

    public void setChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherche = champsRecherche;
    }

    public Set<FichierResultat> getFichierResultats() {
        return fichierResultats;
    }

    public Resultat fichierResultats(Set<FichierResultat> fichierResultats) {
        this.fichierResultats = fichierResultats;
        return this;
    }

    public Resultat addFichierResultat(FichierResultat fichierResultat) {
        this.fichierResultats.add(fichierResultat);
        fichierResultat.setResultat(this);
        return this;
    }

    public Resultat removeFichierResultat(FichierResultat fichierResultat) {
        this.fichierResultats.remove(fichierResultat);
        fichierResultat.setResultat(null);
        return this;
    }

    public void setFichierResultats(Set<FichierResultat> fichierResultats) {
        this.fichierResultats = fichierResultats;
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
        Resultat resultat = (Resultat) o;
        if (resultat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resultat{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
