package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.FormatResultat;

/**
 * A FichierResultat.
 */
@Entity
@Table(name = "fichier_resultat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fichierresultat")
public class FichierResultat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "chemin_fichier", unique = true)
    private String cheminFichier;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private FormatResultat format;

    @NotNull
    @Column(name = "avec_statistiques", nullable = false)
    private Boolean avecStatistiques;

    @OneToMany(mappedBy = "fichierResultat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EnvoiResultat> envoiResultats = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("fichierResultats")
    private Resultat resultat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public FichierResultat cheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
        return this;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public FormatResultat getFormat() {
        return format;
    }

    public FichierResultat format(FormatResultat format) {
        this.format = format;
        return this;
    }

    public void setFormat(FormatResultat format) {
        this.format = format;
    }

    public Boolean isAvecStatistiques() {
        return avecStatistiques;
    }

    public FichierResultat avecStatistiques(Boolean avecStatistiques) {
        this.avecStatistiques = avecStatistiques;
        return this;
    }

    public void setAvecStatistiques(Boolean avecStatistiques) {
        this.avecStatistiques = avecStatistiques;
    }

    public Set<EnvoiResultat> getEnvoiResultats() {
        return envoiResultats;
    }

    public FichierResultat envoiResultats(Set<EnvoiResultat> envoiResultats) {
        this.envoiResultats = envoiResultats;
        return this;
    }

    public FichierResultat addEnvoiResultat(EnvoiResultat envoiResultat) {
        this.envoiResultats.add(envoiResultat);
        envoiResultat.setFichierResultat(this);
        return this;
    }

    public FichierResultat removeEnvoiResultat(EnvoiResultat envoiResultat) {
        this.envoiResultats.remove(envoiResultat);
        envoiResultat.setFichierResultat(null);
        return this;
    }

    public void setEnvoiResultats(Set<EnvoiResultat> envoiResultats) {
        this.envoiResultats = envoiResultats;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public FichierResultat resultat(Resultat resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
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
        FichierResultat fichierResultat = (FichierResultat) o;
        if (fichierResultat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichierResultat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichierResultat{" +
            "id=" + getId() +
            ", cheminFichier='" + getCheminFichier() + "'" +
            ", format='" + getFormat() + "'" +
            ", avecStatistiques='" + isAvecStatistiques() + "'" +
            "}";
    }
}
