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
 * A TypeExtraction.
 */
@Entity
@Table(name = "type_extraction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typeextraction")
public class TypeExtraction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @OneToMany(mappedBy = "typeExtraction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChampsRecherche> champsRecherches = new HashSet<>();
    @OneToMany(mappedBy = "typeExtraction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dimension> dimensions = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "type_extraction_monitor",
               joinColumns = @JoinColumn(name = "type_extraction_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "monitor_id", referencedColumnName = "id"))
    private Set<Monitor> monitors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("typeExtractions")
    private Base base;

    @ManyToOne
    @JsonIgnoreProperties("typeExtractions")
    private Filtre filtre;

    @ManyToOne
    @JsonIgnoreProperties("typeExtractions")
    private Flux flux;

    @ManyToMany(mappedBy = "typeExtractionRequetees")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Colonne> colonneRequetables = new HashSet<>();

    @ManyToMany(mappedBy = "typeExtractions")
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

    public String getCode() {
        return code;
    }

    public TypeExtraction code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeExtraction libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<ChampsRecherche> getChampsRecherches() {
        return champsRecherches;
    }

    public TypeExtraction champsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
        return this;
    }

    public TypeExtraction addChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.add(champsRecherche);
        champsRecherche.setTypeExtraction(this);
        return this;
    }

    public TypeExtraction removeChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.remove(champsRecherche);
        champsRecherche.setTypeExtraction(null);
        return this;
    }

    public void setChampsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public TypeExtraction dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public TypeExtraction addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.setTypeExtraction(this);
        return this;
    }

    public TypeExtraction removeDimension(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.setTypeExtraction(null);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Set<Monitor> getMonitors() {
        return monitors;
    }

    public TypeExtraction monitors(Set<Monitor> monitors) {
        this.monitors = monitors;
        return this;
    }

    public TypeExtraction addMonitor(Monitor monitor) {
        this.monitors.add(monitor);
        monitor.getTypeExtractions().add(this);
        return this;
    }

    public TypeExtraction removeMonitor(Monitor monitor) {
        this.monitors.remove(monitor);
        monitor.getTypeExtractions().remove(this);
        return this;
    }

    public void setMonitors(Set<Monitor> monitors) {
        this.monitors = monitors;
    }

    public Base getBase() {
        return base;
    }

    public TypeExtraction base(Base base) {
        this.base = base;
        return this;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Filtre getFiltre() {
        return filtre;
    }

    public TypeExtraction filtre(Filtre filtre) {
        this.filtre = filtre;
        return this;
    }

    public void setFiltre(Filtre filtre) {
        this.filtre = filtre;
    }

    public Flux getFlux() {
        return flux;
    }

    public TypeExtraction flux(Flux flux) {
        this.flux = flux;
        return this;
    }

    public void setFlux(Flux flux) {
        this.flux = flux;
    }

    public Set<Colonne> getColonneRequetables() {
        return colonneRequetables;
    }

    public TypeExtraction colonneRequetables(Set<Colonne> colonnes) {
        this.colonneRequetables = colonnes;
        return this;
    }

    public TypeExtraction addColonneRequetable(Colonne colonne) {
        this.colonneRequetables.add(colonne);
        colonne.getTypeExtractionRequetees().add(this);
        return this;
    }

    public TypeExtraction removeColonneRequetable(Colonne colonne) {
        this.colonneRequetables.remove(colonne);
        colonne.getTypeExtractionRequetees().remove(this);
        return this;
    }

    public void setColonneRequetables(Set<Colonne> colonnes) {
        this.colonneRequetables = colonnes;
    }

    public Set<Profil> getProfils() {
        return profils;
    }

    public TypeExtraction profils(Set<Profil> profils) {
        this.profils = profils;
        return this;
    }

    public TypeExtraction addProfil(Profil profil) {
        this.profils.add(profil);
        profil.getTypeExtractions().add(this);
        return this;
    }

    public TypeExtraction removeProfil(Profil profil) {
        this.profils.remove(profil);
        profil.getTypeExtractions().remove(this);
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
        TypeExtraction typeExtraction = (TypeExtraction) o;
        if (typeExtraction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeExtraction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeExtraction{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
