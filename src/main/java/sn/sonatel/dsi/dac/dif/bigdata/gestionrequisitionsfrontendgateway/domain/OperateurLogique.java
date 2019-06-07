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
 * A OperateurLogique.
 */
@Entity
@Table(name = "operateur_logique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "operateurlogique")
public class OperateurLogique implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "operateur_logique", nullable = false, unique = true)
    private String operateurLogique;

    @OneToMany(mappedBy = "operateurLogique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Critere> criteres = new HashSet<>();
    @OneToMany(mappedBy = "operateurLogique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Filtre> filtres = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperateurLogique() {
        return operateurLogique;
    }

    public OperateurLogique operateurLogique(String operateurLogique) {
        this.operateurLogique = operateurLogique;
        return this;
    }

    public void setOperateurLogique(String operateurLogique) {
        this.operateurLogique = operateurLogique;
    }

    public Set<Critere> getCriteres() {
        return criteres;
    }

    public OperateurLogique criteres(Set<Critere> criteres) {
        this.criteres = criteres;
        return this;
    }

    public OperateurLogique addCritere(Critere critere) {
        this.criteres.add(critere);
        critere.setOperateurLogique(this);
        return this;
    }

    public OperateurLogique removeCritere(Critere critere) {
        this.criteres.remove(critere);
        critere.setOperateurLogique(null);
        return this;
    }

    public void setCriteres(Set<Critere> criteres) {
        this.criteres = criteres;
    }

    public Set<Filtre> getFiltres() {
        return filtres;
    }

    public OperateurLogique filtres(Set<Filtre> filtres) {
        this.filtres = filtres;
        return this;
    }

    public OperateurLogique addFiltre(Filtre filtre) {
        this.filtres.add(filtre);
        filtre.setOperateurLogique(this);
        return this;
    }

    public OperateurLogique removeFiltre(Filtre filtre) {
        this.filtres.remove(filtre);
        filtre.setOperateurLogique(null);
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
        OperateurLogique operateurLogique = (OperateurLogique) o;
        if (operateurLogique.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operateurLogique.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperateurLogique{" +
            "id=" + getId() +
            ", operateurLogique='" + getOperateurLogique() + "'" +
            "}";
    }
}
