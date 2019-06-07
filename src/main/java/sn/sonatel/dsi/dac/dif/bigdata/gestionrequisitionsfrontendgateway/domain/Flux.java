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
 * A Flux.
 */
@Entity
@Table(name = "flux")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "flux")
public class Flux implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "flux")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Colonne> colonnes = new HashSet<>();
    @OneToMany(mappedBy = "flux")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dimension> dimensions = new HashSet<>();
    @OneToMany(mappedBy = "flux")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Jointure> jointures = new HashSet<>();
    @OneToMany(mappedBy = "flux")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeExtraction> typeExtractions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("fluxes")
    private Base base;

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

    public Flux libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Flux description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Colonne> getColonnes() {
        return colonnes;
    }

    public Flux colonnes(Set<Colonne> colonnes) {
        this.colonnes = colonnes;
        return this;
    }

    public Flux addColonne(Colonne colonne) {
        this.colonnes.add(colonne);
        colonne.setFlux(this);
        return this;
    }

    public Flux removeColonne(Colonne colonne) {
        this.colonnes.remove(colonne);
        colonne.setFlux(null);
        return this;
    }

    public void setColonnes(Set<Colonne> colonnes) {
        this.colonnes = colonnes;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public Flux dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Flux addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.setFlux(this);
        return this;
    }

    public Flux removeDimension(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.setFlux(null);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Set<Jointure> getJointures() {
        return jointures;
    }

    public Flux jointures(Set<Jointure> jointures) {
        this.jointures = jointures;
        return this;
    }

    public Flux addJointure(Jointure jointure) {
        this.jointures.add(jointure);
        jointure.setFlux(this);
        return this;
    }

    public Flux removeJointure(Jointure jointure) {
        this.jointures.remove(jointure);
        jointure.setFlux(null);
        return this;
    }

    public void setJointures(Set<Jointure> jointures) {
        this.jointures = jointures;
    }

    public Set<TypeExtraction> getTypeExtractions() {
        return typeExtractions;
    }

    public Flux typeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
        return this;
    }

    public Flux addTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.add(typeExtraction);
        typeExtraction.setFlux(this);
        return this;
    }

    public Flux removeTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.remove(typeExtraction);
        typeExtraction.setFlux(null);
        return this;
    }

    public void setTypeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
    }

    public Base getBase() {
        return base;
    }

    public Flux base(Base base) {
        this.base = base;
        return this;
    }

    public void setBase(Base base) {
        this.base = base;
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
        Flux flux = (Flux) o;
        if (flux.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), flux.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Flux{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
