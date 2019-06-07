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
 * A Clause.
 */
@Entity
@Table(name = "clause")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "clause")
public class Clause implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prefixe")
    private String prefixe;

    @Column(name = "suffixe")
    private String suffixe;

    @OneToMany(mappedBy = "clause")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Critere> criteres = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "clause_operande",
               joinColumns = @JoinColumn(name = "clause_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "operande_id", referencedColumnName = "id"))
    private Set<Operande> operandes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("clauses")
    private Operateur operateur;

    @ManyToMany(mappedBy = "clauses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Filtre> filtres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefixe() {
        return prefixe;
    }

    public Clause prefixe(String prefixe) {
        this.prefixe = prefixe;
        return this;
    }

    public void setPrefixe(String prefixe) {
        this.prefixe = prefixe;
    }

    public String getSuffixe() {
        return suffixe;
    }

    public Clause suffixe(String suffixe) {
        this.suffixe = suffixe;
        return this;
    }

    public void setSuffixe(String suffixe) {
        this.suffixe = suffixe;
    }

    public Set<Critere> getCriteres() {
        return criteres;
    }

    public Clause criteres(Set<Critere> criteres) {
        this.criteres = criteres;
        return this;
    }

    public Clause addCritere(Critere critere) {
        this.criteres.add(critere);
        critere.setClause(this);
        return this;
    }

    public Clause removeCritere(Critere critere) {
        this.criteres.remove(critere);
        critere.setClause(null);
        return this;
    }

    public void setCriteres(Set<Critere> criteres) {
        this.criteres = criteres;
    }

    public Set<Operande> getOperandes() {
        return operandes;
    }

    public Clause operandes(Set<Operande> operandes) {
        this.operandes = operandes;
        return this;
    }

    public Clause addOperande(Operande operande) {
        this.operandes.add(operande);
        operande.getClauses().add(this);
        return this;
    }

    public Clause removeOperande(Operande operande) {
        this.operandes.remove(operande);
        operande.getClauses().remove(this);
        return this;
    }

    public void setOperandes(Set<Operande> operandes) {
        this.operandes = operandes;
    }

    public Operateur getOperateur() {
        return operateur;
    }

    public Clause operateur(Operateur operateur) {
        this.operateur = operateur;
        return this;
    }

    public void setOperateur(Operateur operateur) {
        this.operateur = operateur;
    }

    public Set<Filtre> getFiltres() {
        return filtres;
    }

    public Clause filtres(Set<Filtre> filtres) {
        this.filtres = filtres;
        return this;
    }

    public Clause addFiltre(Filtre filtre) {
        this.filtres.add(filtre);
        filtre.getClauses().add(this);
        return this;
    }

    public Clause removeFiltre(Filtre filtre) {
        this.filtres.remove(filtre);
        filtre.getClauses().remove(this);
        return this;
    }

    public void setFiltres(Set<Filtre> filtres) {
        this.filtres = filtres;
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
        Clause clause = (Clause) o;
        if (clause.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clause.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Clause{" +
            "id=" + getId() +
            ", prefixe='" + getPrefixe() + "'" +
            ", suffixe='" + getSuffixe() + "'" +
            "}";
    }
}
