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
 * A Environnement.
 */
@Entity
@Table(name = "environnement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "environnement")
public class Environnement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @OneToMany(mappedBy = "environnement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChampsRecherche> champsRecherches = new HashSet<>();
    @ManyToMany(mappedBy = "environnements")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Filiale> filiales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Environnement code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Environnement libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<ChampsRecherche> getChampsRecherches() {
        return champsRecherches;
    }

    public Environnement champsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
        return this;
    }

    public Environnement addChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.add(champsRecherche);
        champsRecherche.setEnvironnement(this);
        return this;
    }

    public Environnement removeChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.remove(champsRecherche);
        champsRecherche.setEnvironnement(null);
        return this;
    }

    public void setChampsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
    }

    public Set<Filiale> getFiliales() {
        return filiales;
    }

    public Environnement filiales(Set<Filiale> filiales) {
        this.filiales = filiales;
        return this;
    }

    public Environnement addFiliale(Filiale filiale) {
        this.filiales.add(filiale);
        filiale.getEnvironnements().add(this);
        return this;
    }

    public Environnement removeFiliale(Filiale filiale) {
        this.filiales.remove(filiale);
        filiale.getEnvironnements().remove(this);
        return this;
    }

    public void setFiliales(Set<Filiale> filiales) {
        this.filiales = filiales;
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
        Environnement environnement = (Environnement) o;
        if (environnement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), environnement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Environnement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
