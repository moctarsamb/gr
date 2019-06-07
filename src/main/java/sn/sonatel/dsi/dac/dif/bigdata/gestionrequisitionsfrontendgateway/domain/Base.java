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

/**
 * A Base.
 */
@Entity
@Table(name = "base")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "base")
public class Base implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    
    @Column(name = "description", unique = true)
    private String description;

    @OneToMany(mappedBy = "base")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dimension> dimensions = new HashSet<>();
    @OneToMany(mappedBy = "base")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Flux> fluxes = new HashSet<>();
    @OneToMany(mappedBy = "base")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeExtraction> typeExtractions = new HashSet<>();
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

    public Base libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Base description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public Base dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Base addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.setBase(this);
        return this;
    }

    public Base removeDimension(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.setBase(null);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Set<Flux> getFluxes() {
        return fluxes;
    }

    public Base fluxes(Set<Flux> fluxes) {
        this.fluxes = fluxes;
        return this;
    }

    public Base addFlux(Flux flux) {
        this.fluxes.add(flux);
        flux.setBase(this);
        return this;
    }

    public Base removeFlux(Flux flux) {
        this.fluxes.remove(flux);
        flux.setBase(null);
        return this;
    }

    public void setFluxes(Set<Flux> fluxes) {
        this.fluxes = fluxes;
    }

    public Set<TypeExtraction> getTypeExtractions() {
        return typeExtractions;
    }

    public Base typeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
        return this;
    }

    public Base addTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.add(typeExtraction);
        typeExtraction.setBase(this);
        return this;
    }

    public Base removeTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.remove(typeExtraction);
        typeExtraction.setBase(null);
        return this;
    }

    public void setTypeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
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
        Base base = (Base) o;
        if (base.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), base.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Base{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
