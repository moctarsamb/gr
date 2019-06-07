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

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.TypeMonitor;

/**
 * A Monitor.
 */
@Entity
@Table(name = "monitor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "monitor")
public class Monitor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypeMonitor type;

    @ManyToOne
    @JsonIgnoreProperties("monitors")
    private Colonne colonne;

    @ManyToOne
    @JsonIgnoreProperties("monitors")
    private Fonction fonction;

    @ManyToMany(mappedBy = "monitors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Dimension> dimensions = new HashSet<>();

    @ManyToMany(mappedBy = "monitors")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TypeExtraction> typeExtractions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeMonitor getType() {
        return type;
    }

    public Monitor type(TypeMonitor type) {
        this.type = type;
        return this;
    }

    public void setType(TypeMonitor type) {
        this.type = type;
    }

    public Colonne getColonne() {
        return colonne;
    }

    public Monitor colonne(Colonne colonne) {
        this.colonne = colonne;
        return this;
    }

    public void setColonne(Colonne colonne) {
        this.colonne = colonne;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Monitor fonction(Fonction fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public Monitor dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Monitor addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.getMonitors().add(this);
        return this;
    }

    public Monitor removeDimension(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.getMonitors().remove(this);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public Set<TypeExtraction> getTypeExtractions() {
        return typeExtractions;
    }

    public Monitor typeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
        return this;
    }

    public Monitor addTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.add(typeExtraction);
        typeExtraction.getMonitors().add(this);
        return this;
    }

    public Monitor removeTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.remove(typeExtraction);
        typeExtraction.getMonitors().remove(this);
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
        Monitor monitor = (Monitor) o;
        if (monitor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monitor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Monitor{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
