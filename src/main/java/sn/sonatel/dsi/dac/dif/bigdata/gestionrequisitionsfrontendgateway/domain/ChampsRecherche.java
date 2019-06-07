package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ChampsRecherche.
 */
@Entity
@Table(name = "champs_recherche")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "champsrecherche")
public class ChampsRecherche implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "champs", nullable = false)
    private String champs;

    @NotNull
    @Column(name = "date_debut_extraction", nullable = false)
    private LocalDate dateDebutExtraction;

    @NotNull
    @Column(name = "date_fin_extraction", nullable = false)
    private LocalDate dateFinExtraction;

    @OneToOne(mappedBy = "champsRecherche")
    @JsonIgnore
    private Resultat resultat;

    @ManyToOne
    @JsonIgnoreProperties("champsRecherches")
    private Colonne colonne;

    @ManyToOne
    @JsonIgnoreProperties("champsRecherches")
    private Environnement environnement;

    @ManyToOne
    @JsonIgnoreProperties("champsRecherches")
    private Filiale filiale;

    @ManyToOne
    @JsonIgnoreProperties("champsRecherches")
    private Requisition requisition;

    @ManyToOne
    @JsonIgnoreProperties("champsRecherches")
    private TypeExtraction typeExtraction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChamps() {
        return champs;
    }

    public ChampsRecherche champs(String champs) {
        this.champs = champs;
        return this;
    }

    public void setChamps(String champs) {
        this.champs = champs;
    }

    public LocalDate getDateDebutExtraction() {
        return dateDebutExtraction;
    }

    public ChampsRecherche dateDebutExtraction(LocalDate dateDebutExtraction) {
        this.dateDebutExtraction = dateDebutExtraction;
        return this;
    }

    public void setDateDebutExtraction(LocalDate dateDebutExtraction) {
        this.dateDebutExtraction = dateDebutExtraction;
    }

    public LocalDate getDateFinExtraction() {
        return dateFinExtraction;
    }

    public ChampsRecherche dateFinExtraction(LocalDate dateFinExtraction) {
        this.dateFinExtraction = dateFinExtraction;
        return this;
    }

    public void setDateFinExtraction(LocalDate dateFinExtraction) {
        this.dateFinExtraction = dateFinExtraction;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public ChampsRecherche resultat(Resultat resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public Colonne getColonne() {
        return colonne;
    }

    public ChampsRecherche colonne(Colonne colonne) {
        this.colonne = colonne;
        return this;
    }

    public void setColonne(Colonne colonne) {
        this.colonne = colonne;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public ChampsRecherche environnement(Environnement environnement) {
        this.environnement = environnement;
        return this;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }

    public Filiale getFiliale() {
        return filiale;
    }

    public ChampsRecherche filiale(Filiale filiale) {
        this.filiale = filiale;
        return this;
    }

    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public ChampsRecherche requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public TypeExtraction getTypeExtraction() {
        return typeExtraction;
    }

    public ChampsRecherche typeExtraction(TypeExtraction typeExtraction) {
        this.typeExtraction = typeExtraction;
        return this;
    }

    public void setTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtraction = typeExtraction;
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
        ChampsRecherche champsRecherche = (ChampsRecherche) o;
        if (champsRecherche.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), champsRecherche.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChampsRecherche{" +
            "id=" + getId() +
            ", champs='" + getChamps() + "'" +
            ", dateDebutExtraction='" + getDateDebutExtraction() + "'" +
            ", dateFinExtraction='" + getDateFinExtraction() + "'" +
            "}";
    }
}
