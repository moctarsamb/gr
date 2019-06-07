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
 * A Operande.
 */
@Entity
@Table(name = "operande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operande")
public class Operande implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "operande_colonne",
               joinColumns = @JoinColumn(name = "operande_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "colonne_id", referencedColumnName = "id"))
    private Set<Colonne> colonnes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "operande_valeur",
               joinColumns = @JoinColumn(name = "operande_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "valeur_id", referencedColumnName = "id"))
    private Set<Valeur> valeurs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("operandes")
    private Fonction fonction;

    @ManyToMany(mappedBy = "operandes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Clause> clauses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Colonne> getColonnes() {
        return colonnes;
    }

    public Operande colonnes(Set<Colonne> colonnes) {
        this.colonnes = colonnes;
        return this;
    }

    public Operande addColonne(Colonne colonne) {
        this.colonnes.add(colonne);
        colonne.getOperandes().add(this);
        return this;
    }

    public Operande removeColonne(Colonne colonne) {
        this.colonnes.remove(colonne);
        colonne.getOperandes().remove(this);
        return this;
    }

    public void setColonnes(Set<Colonne> colonnes) {
        this.colonnes = colonnes;
    }

    public Set<Valeur> getValeurs() {
        return valeurs;
    }

    public Operande valeurs(Set<Valeur> valeurs) {
        this.valeurs = valeurs;
        return this;
    }

    public Operande addValeur(Valeur valeur) {
        this.valeurs.add(valeur);
        valeur.getOperandes().add(this);
        return this;
    }

    public Operande removeValeur(Valeur valeur) {
        this.valeurs.remove(valeur);
        valeur.getOperandes().remove(this);
        return this;
    }

    public void setValeurs(Set<Valeur> valeurs) {
        this.valeurs = valeurs;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Operande fonction(Fonction fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Set<Clause> getClauses() {
        return clauses;
    }

    public Operande clauses(Set<Clause> clauses) {
        this.clauses = clauses;
        return this;
    }

    public Operande addClause(Clause clause) {
        this.clauses.add(clause);
        clause.getOperandes().add(this);
        return this;
    }

    public Operande removeClause(Clause clause) {
        this.clauses.remove(clause);
        clause.getOperandes().remove(this);
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
        Operande operande = (Operande) o;
        if (operande.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operande.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Operande{" +
            "id=" + getId() +
            "}";
    }
}
