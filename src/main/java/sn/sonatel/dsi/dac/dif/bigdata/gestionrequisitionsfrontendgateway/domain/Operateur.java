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
 * A Operateur.
 */
@Entity
@Table(name = "operateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operateur")
public class Operateur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "operateur", nullable = false, unique = true)
    private String operateur;

    @OneToMany(mappedBy = "operateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Clause> clauses = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperateur() {
        return operateur;
    }

    public Operateur operateur(String operateur) {
        this.operateur = operateur;
        return this;
    }

    public void setOperateur(String operateur) {
        this.operateur = operateur;
    }

    public Set<Clause> getClauses() {
        return clauses;
    }

    public Operateur clauses(Set<Clause> clauses) {
        this.clauses = clauses;
        return this;
    }

    public Operateur addClause(Clause clause) {
        this.clauses.add(clause);
        clause.setOperateur(this);
        return this;
    }

    public Operateur removeClause(Clause clause) {
        this.clauses.remove(clause);
        clause.setOperateur(null);
        return this;
    }

    public void setClauses(Set<Clause> clauses) {
        this.clauses = clauses;
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
        Operateur operateur = (Operateur) o;
        if (operateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operateur{" +
            "id=" + getId() +
            ", operateur='" + getOperateur() + "'" +
            "}";
    }
}
