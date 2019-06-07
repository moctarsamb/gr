package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dimension.
 */
@Entity
@Table(name = "dimension")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dimension")
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dimension_monitor",
               joinColumns = @JoinColumn(name = "dimension_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "monitor_id", referencedColumnName = "id"))
    private Set<Monitor> monitors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dimensions")
    private Base base;

    @ManyToOne
    @JsonIgnoreProperties("dimensions")
    private Flux flux;

    @ManyToOne
    @JsonIgnoreProperties("dimensions")
    private TypeExtraction typeExtraction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Monitor> getMonitors() {
        return monitors;
    }

    public Dimension monitors(Set<Monitor> monitors) {
        this.monitors = monitors;
        return this;
    }

    public Dimension addMonitor(Monitor monitor) {
        this.monitors.add(monitor);
        monitor.getDimensions().add(this);
        return this;
    }

    public Dimension removeMonitor(Monitor monitor) {
        this.monitors.remove(monitor);
        monitor.getDimensions().remove(this);
        return this;
    }

    public void setMonitors(Set<Monitor> monitors) {
        this.monitors = monitors;
    }

    public Base getBase() {
        return base;
    }

    public Dimension base(Base base) {
        this.base = base;
        return this;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Flux getFlux() {
        return flux;
    }

    public Dimension flux(Flux flux) {
        this.flux = flux;
        return this;
    }

    public void setFlux(Flux flux) {
        this.flux = flux;
    }

    public TypeExtraction getTypeExtraction() {
        return typeExtraction;
    }

    public Dimension typeExtraction(TypeExtraction typeExtraction) {
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
        Dimension dimension = (Dimension) o;
        if (dimension.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimension.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dimension{" +
            "id=" + getId() +
            "}";
    }
}
