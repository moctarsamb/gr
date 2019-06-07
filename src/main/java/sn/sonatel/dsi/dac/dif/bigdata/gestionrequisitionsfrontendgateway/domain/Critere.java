package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Critere.
 */
@Entity
@Table(name = "critere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "critere")
public class Critere implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "critere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Jointure> jointures = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("criteres")
    private Clause clause;

    @ManyToOne
    @JsonIgnoreProperties("criteres")
    private OperateurLogique operateurLogique;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Jointure> getJointures() {
        return jointures;
    }

    public Critere jointures(Set<Jointure> jointures) {
        this.jointures = jointures;
        return this;
    }

    public Critere addJointure(Jointure jointure) {
        this.jointures.add(jointure);
        jointure.setCritere(this);
        return this;
    }

    public Critere removeJointure(Jointure jointure) {
        this.jointures.remove(jointure);
        jointure.setCritere(null);
        return this;
    }

    public void setJointures(Set<Jointure> jointures) {
        this.jointures = jointures;
    }

    public Clause getClause() {
        return clause;
    }

    public Critere clause(Clause clause) {
        this.clause = clause;
        return this;
    }

    public void setClause(Clause clause) {
        this.clause = clause;
    }

    public OperateurLogique getOperateurLogique() {
        return operateurLogique;
    }

    public Critere operateurLogique(OperateurLogique operateurLogique) {
        this.operateurLogique = operateurLogique;
        return this;
    }

    public void setOperateurLogique(OperateurLogique operateurLogique) {
        this.operateurLogique = operateurLogique;
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
        Critere critere = (Critere) o;
        if (critere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), critere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Critere{" +
            "id=" + getId() +
            "}";
    }
}
