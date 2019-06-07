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
 * A TypeJointure.
 */
@Entity
@Table(name = "type_jointure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "typejointure")
public class TypeJointure implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "typeJointure")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Jointure> jointures = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public TypeJointure type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Jointure> getJointures() {
        return jointures;
    }

    public TypeJointure jointures(Set<Jointure> jointures) {
        this.jointures = jointures;
        return this;
    }

    public TypeJointure addJointure(Jointure jointure) {
        this.jointures.add(jointure);
        jointure.setTypeJointure(this);
        return this;
    }

    public TypeJointure removeJointure(Jointure jointure) {
        this.jointures.remove(jointure);
        jointure.setTypeJointure(null);
        return this;
    }

    public void setJointures(Set<Jointure> jointures) {
        this.jointures = jointures;
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
        TypeJointure typeJointure = (TypeJointure) o;
        if (typeJointure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeJointure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeJointure{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
