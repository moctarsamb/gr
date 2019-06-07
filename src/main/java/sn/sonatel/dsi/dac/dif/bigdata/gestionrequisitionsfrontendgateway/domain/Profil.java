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
 * A Profil.
 */
@Entity
@Table(name = "profil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profil")
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @NotNull
    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @OneToMany(mappedBy = "profil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profil_colonne",
               joinColumns = @JoinColumn(name = "profil_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "colonne_id", referencedColumnName = "id"))
    private Set<Colonne> colonnes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profil_type_extraction",
               joinColumns = @JoinColumn(name = "profil_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "type_extraction_id", referencedColumnName = "id"))
    private Set<TypeExtraction> typeExtractions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("profilAdministres")
    private Utilisateur administrateurProfil;

    @ManyToMany(mappedBy = "profils")
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

    public String getLibelle() {
        return libelle;
    }

    public Profil libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Profil description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Profil utilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        return this;
    }

    public Profil addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
        utilisateur.setProfil(this);
        return this;
    }

    public Profil removeUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.setProfil(null);
        return this;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Set<Colonne> getColonnes() {
        return colonnes;
    }

    public Profil colonnes(Set<Colonne> colonnes) {
        this.colonnes = colonnes;
        return this;
    }

    public Profil addColonne(Colonne colonne) {
        this.colonnes.add(colonne);
        colonne.getProfils().add(this);
        return this;
    }

    public Profil removeColonne(Colonne colonne) {
        this.colonnes.remove(colonne);
        colonne.getProfils().remove(this);
        return this;
    }

    public void setColonnes(Set<Colonne> colonnes) {
        this.colonnes = colonnes;
    }

    public Set<TypeExtraction> getTypeExtractions() {
        return typeExtractions;
    }

    public Profil typeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
        return this;
    }

    public Profil addTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.add(typeExtraction);
        typeExtraction.getProfils().add(this);
        return this;
    }

    public Profil removeTypeExtraction(TypeExtraction typeExtraction) {
        this.typeExtractions.remove(typeExtraction);
        typeExtraction.getProfils().remove(this);
        return this;
    }

    public void setTypeExtractions(Set<TypeExtraction> typeExtractions) {
        this.typeExtractions = typeExtractions;
    }

    public Utilisateur getAdministrateurProfil() {
        return administrateurProfil;
    }

    public Profil administrateurProfil(Utilisateur utilisateur) {
        this.administrateurProfil = utilisateur;
        return this;
    }

    public void setAdministrateurProfil(Utilisateur utilisateur) {
        this.administrateurProfil = utilisateur;
    }

    public Set<Filiale> getFiliales() {
        return filiales;
    }

    public Profil filiales(Set<Filiale> filiales) {
        this.filiales = filiales;
        return this;
    }

    public Profil addFiliale(Filiale filiale) {
        this.filiales.add(filiale);
        filiale.getProfils().add(this);
        return this;
    }

    public Profil removeFiliale(Filiale filiale) {
        this.filiales.remove(filiale);
        filiale.getProfils().remove(this);
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
        Profil profil = (Profil) o;
        if (profil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profil{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
