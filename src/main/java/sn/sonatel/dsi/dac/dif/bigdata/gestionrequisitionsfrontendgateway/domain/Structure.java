package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Structure.
 */
@Entity
@Table(name = "structure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "structure")
public class Structure implements Serializable {

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

    @OneToMany(mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requisition> requisitions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("structures")
    private Filiale filiale;

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

    public Structure code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Structure libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Requisition> getRequisitions() {
        return requisitions;
    }

    public Structure requisitions(Set<Requisition> requisitions) {
        this.requisitions = requisitions;
        return this;
    }

    public Structure addRequisition(Requisition requisition) {
        this.requisitions.add(requisition);
        requisition.setStructure(this);
        return this;
    }

    public Structure removeRequisition(Requisition requisition) {
        this.requisitions.remove(requisition);
        requisition.setStructure(null);
        return this;
    }

    public void setRequisitions(Set<Requisition> requisitions) {
        this.requisitions = requisitions;
    }

    public Filiale getFiliale() {
        return filiale;
    }

    public Structure filiale(Filiale filiale) {
        this.filiale = filiale;
        return this;
    }

    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
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
        Structure structure = (Structure) o;
        if (structure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), structure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Structure{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
