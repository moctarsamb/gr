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
 * A Fonction.
 */
@Entity
@Table(name = "fonction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fonction")
public class Fonction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "fonction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Monitor> monitors = new HashSet<>();
    @OneToMany(mappedBy = "fonction")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operande> operandes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Fonction nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Monitor> getMonitors() {
        return monitors;
    }

    public Fonction monitors(Set<Monitor> monitors) {
        this.monitors = monitors;
        return this;
    }

    public Fonction addMonitor(Monitor monitor) {
        this.monitors.add(monitor);
        monitor.setFonction(this);
        return this;
    }

    public Fonction removeMonitor(Monitor monitor) {
        this.monitors.remove(monitor);
        monitor.setFonction(null);
        return this;
    }

    public void setMonitors(Set<Monitor> monitors) {
        this.monitors = monitors;
    }

    public Set<Operande> getOperandes() {
        return operandes;
    }

    public Fonction operandes(Set<Operande> operandes) {
        this.operandes = operandes;
        return this;
    }

    public Fonction addOperande(Operande operande) {
        this.operandes.add(operande);
        operande.setFonction(this);
        return this;
    }

    public Fonction removeOperande(Operande operande) {
        this.operandes.remove(operande);
        operande.setFonction(null);
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
        Fonction fonction = (Fonction) o;
        if (fonction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fonction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fonction{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
