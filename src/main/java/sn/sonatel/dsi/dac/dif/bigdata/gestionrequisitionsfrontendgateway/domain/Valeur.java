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
 * A Valeur.
 */
@Entity
@Table(name = "valeur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "valeur")
public class Valeur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valeur", nullable = false)
    private String valeur;

    @ManyToMany(mappedBy = "valeurs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Operande> operandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValeur() {
        return valeur;
    }

    public Valeur valeur(String valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public Set<Operande> getOperandes() {
        return operandes;
    }

    public Valeur operandes(Set<Operande> operandes) {
        this.operandes = operandes;
        return this;
    }

    public Valeur addOperande(Operande operande) {
        this.operandes.add(operande);
        operande.getValeurs().add(this);
        return this;
    }

    public Valeur removeOperande(Operande operande) {
        this.operandes.remove(operande);
        operande.getValeurs().remove(this);
        return this;
    }

    public void setOperandes(Set<Operande> operandes) {
        this.operandes = operandes;
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
        Valeur valeur = (Valeur) o;
        if (valeur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valeur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Valeur{" +
            "id=" + getId() +
            ", valeur='" + getValeur() + "'" +
            "}";
    }
}
