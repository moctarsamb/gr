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

/**
 * A Colonne.
 */
@Entity
@Table(name = "colonne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "colonne")
public class Colonne implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    
    @Column(name = "description", unique = true)
    private String description;

    @Column(name = "alias")
    private String alias;

    @OneToMany(mappedBy = "colonne")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChampsRecherche> champsRecherches = new HashSet<>();
    @OneToMany(mappedBy = "colonne")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Monitor> monitors = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "colonne_type_extraction_requetee",
               joinColumns = @JoinColumn(name = "colonne_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "type_extraction_requetee_id", referencedColumnName = "id"))
    private Set<TypeExtraction> typeExtractionRequetees = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("colonnes")
    private Flux flux;

    @ManyToMany(mappedBy = "colonnes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Operande> operandes = new HashSet<>();

    @ManyToMany(mappedBy = "colonnes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Profil> profils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Colonne libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Colonne description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public Colonne alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Set<ChampsRecherche> getChampsRecherches() {
        return champsRecherches;
    }

    public Colonne champsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
        return this;
    }

    public Colonne addChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.add(champsRecherche);
        champsRecherche.setColonne(this);
        return this;
    }

    public Colonne removeChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.remove(champsRecherche);
        champsRecherche.setColonne(null);
        return this;
    }

    public void setChampsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
    }

    public Set<Monitor> getMonitors() {
        return monitors;
    }

    public Colonne monitors(Set<Monitor> monitors) {
        this.monitors = monitors;
        return this;
    }

    public Colonne addMonitor(Monitor monitor) {
        this.monitors.add(monitor);
        monitor.setColonne(this);
        return this;
    }

    public Colonne removeMonitor(Monitor monitor) {
        this.monitors.remove(monitor);
        monitor.setColonne(null);
        return this;
    }

    public void setMonitors(Set<Monitor> monitors) {
        this.monitors = monitors;
    }

    public Set<TypeExtraction> getTypeExtractionRequetees() {
        return typeExtractionRequetees;
    }

    public Colonne typeExtractionRequetees(Set<TypeExtraction> typeExtractions) {
        this.typeExtractionRequetees = typeExtractions;
        return this;
    }

    public Colonne addTypeExtractionRequetee(TypeExtraction typeExtraction) {
        this.typeExtractionRequetees.add(typeExtraction);
        typeExtraction.getColonneRequetables().add(this);
        return this;
    }

    public Colonne removeTypeExtractionRequetee(TypeExtraction typeExtraction) {
        this.typeExtractionRequetees.remove(typeExtraction);
        typeExtraction.getColonneRequetables().remove(this);
        return this;
    }

    public void setTypeExtractionRequetees(Set<TypeExtraction> typeExtractions) {
        this.typeExtractionRequetees = typeExtractions;
    }

    public Flux getFlux() {
        return flux;
    }

    public Colonne flux(Flux flux) {
        this.flux = flux;
        return this;
    }

    public void setFlux(Flux flux) {
        this.flux = flux;
    }

    public Set<Operande> getOperandes() {
        return operandes;
    }

    public Colonne operandes(Set<Operande> operandes) {
        this.operandes = operandes;
        return this;
    }

    public Colonne addOperande(Operande operande) {
        this.operandes.add(operande);
        operande.getColonnes().add(this);
        return this;
    }

    public Colonne removeOperande(Operande operande) {
        this.operandes.remove(operande);
        operande.getColonnes().remove(this);
        return this;
    }

    public void setOperandes(Set<Operande> operandes) {
        this.operandes = operandes;
    }

    public Set<Profil> getProfils() {
        return profils;
    }

    public Colonne profils(Set<Profil> profils) {
        this.profils = profils;
        return this;
    }

    public Colonne addProfil(Profil profil) {
        this.profils.add(profil);
        profil.getColonnes().add(this);
        return this;
    }

    public Colonne removeProfil(Profil profil) {
        this.profils.remove(profil);
        profil.getColonnes().remove(this);
        return this;
    }

    public void setProfils(Set<Profil> profils) {
        this.profils = profils;
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
        Colonne colonne = (Colonne) o;
        if (colonne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), colonne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Colonne{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", alias='" + getAlias() + "'" +
            "}";
    }
}
