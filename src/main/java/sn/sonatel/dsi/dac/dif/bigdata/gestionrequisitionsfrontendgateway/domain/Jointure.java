package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Jointure.
 */
@Entity
@Table(name = "jointure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jointure")
public class Jointure implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("jointures")
    private Critere critere;

    @ManyToOne
    @JsonIgnoreProperties("jointures")
    private Flux flux;

    @ManyToOne
    @JsonIgnoreProperties("jointures")
    private TypeJointure typeJointure;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Critere getCritere() {
        return critere;
    }

    public Jointure critere(Critere critere) {
        this.critere = critere;
        return this;
    }

    public void setCritere(Critere critere) {
        this.critere = critere;
    }

    public Flux getFlux() {
        return flux;
    }

    public Jointure flux(Flux flux) {
        this.flux = flux;
        return this;
    }

    public void setFlux(Flux flux) {
        this.flux = flux;
    }

    public TypeJointure getTypeJointure() {
        return typeJointure;
    }

    public Jointure typeJointure(TypeJointure typeJointure) {
        this.typeJointure = typeJointure;
        return this;
    }

    public void setTypeJointure(TypeJointure typeJointure) {
        this.typeJointure = typeJointure;
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
        Jointure jointure = (Jointure) o;
        if (jointure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jointure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jointure{" +
            "id=" + getId() +
            "}";
    }
}
