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
 * A Filtre.
 */
@Entity
@Table(name = "filtre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "filtre")
public class Filtre implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "filtre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeExtraction> typeExtractions = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "filtre_clause",
               joinColumns = @JoinColumn(name = "filtre_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "clause_id", referencedColumnName = "id"))
    private Set<Clause> clauses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("filtres")
    private OperateurLogique operateurLogique;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TypeExtraction> getTypeExtractions() {
        return typeExtractions;
    }

    public Filtre typeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
        return this;
    }

    public Filtre addTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.add(typeExtraction);
        typeExtraction.setFiltre(this);
        return this;
    }

    public Filtre removeTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.remove(typeExtraction);
        typeExtraction.setFiltre(null);
        return this;
    }

    public void setTypeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
    }

    public Set<Clause> getClauses() {
        return clauses;
    }

    public Filtre clauses(Set<Clause> clauses) {
        this.clauses = clauses;
        return this;
    }

    public Filtre addClause(Clause clause) {
        this.clauses.add(clause);
        clause.getFiltres().add(this);
        return this;
    }

    public Filtre removeClause(Clause clause) {
        this.clauses.remove(clause);
        clause.getFiltres().remove(this);
        return this;
    }

    public void setClauses(Set<Clause> clauses) {
        this.clauses = clauses;
    }

    public OperateurLogique getOperateurLogique() {
        return operateurLogique;
    }

    public Filtre operateurLogique(OperateurLogique operateurLogique) {
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
        Filtre filtre = (Filtre) o;
        if (filtre.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filtre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filtre{" +
            "id=" + getId() +
            "}";
    }
}
