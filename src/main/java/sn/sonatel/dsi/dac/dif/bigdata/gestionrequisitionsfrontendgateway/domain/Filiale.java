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
 * A Filiale.
 */
@Entity
@Table(name = "filiale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "filiale")
public class Filiale implements Serializable {

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

    @OneToMany(mappedBy = "filiale")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChampsRecherche> champsRecherches = new HashSet<>();
    @OneToMany(mappedBy = "filiale")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Structure> structures = new HashSet<>();
    @OneToMany(mappedBy = "filiale")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "filiale_environnement",
               joinColumns = @JoinColumn(name = "filiale_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "environnement_id", referencedColumnName = "id"))
    private Set<Environnement> environnements = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "filiale_profil",
               joinColumns = @JoinColumn(name = "filiale_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profil_id", referencedColumnName = "id"))
    private Set<Profil> profils = new HashSet<>();

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

    public Filiale code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Filiale libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<ChampsRecherche> getChampsRecherches() {
        return champsRecherches;
    }

    public Filiale champsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
        return this;
    }

    public Filiale addChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.add(champsRecherche);
        champsRecherche.setFiliale(this);
        return this;
    }

    public Filiale removeChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.remove(champsRecherche);
        champsRecherche.setFiliale(null);
        return this;
    }

    public void setChampsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
    }

    public Set<Structure> getStructures() {
        return structures;
    }

    public Filiale structures(Set<Structure> structures) {
        this.structures = structures;
        return this;
    }

    public Filiale addStructure(Structure structure) {
        this.structures.add(structure);
        structure.setFiliale(this);
        return this;
    }

    public Filiale removeStructure(Structure structure) {
        this.structures.remove(structure);
        structure.setFiliale(null);
        return this;
    }

    public void setStructures(Set<Structure> structures) {
        this.structures = structures;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Filiale utilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        return this;
    }

    public Filiale addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
        utilisateur.setFiliale(this);
        return this;
    }

    public Filiale removeUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.setFiliale(null);
        return this;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Set<Environnement> getEnvironnements() {
        return environnements;
    }

    public Filiale environnements(Set<Environnement> environnements) {
        this.environnements = environnements;
        return this;
    }

    public Filiale addEnvironnement(Environnement environnement) {
        this.environnements.add(environnement);
        environnement.getFiliales().add(this);
        return this;
    }

    public Filiale removeEnvironnement(Environnement environnement) {
        this.environnements.remove(environnement);
        environnement.getFiliales().remove(this);
        return this;
    }

    public void setEnvironnements(Set<Environnement> environnements) {
        this.environnements = environnements;
    }

    public Set<Profil> getProfils() {
        return profils;
    }

    public Filiale profils(Set<Profil> profils) {
        this.profils = profils;
        return this;
    }

    public Filiale addProfil(Profil profil) {
        this.profils.add(profil);
        profil.getFiliales().add(this);
        return this;
    }

    public Filiale removeProfil(Profil profil) {
        this.profils.remove(profil);
        profil.getFiliales().remove(this);
        return this;
    }

    public void setProfils(Set<Profil> profils) {
        this.profils = profils;
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
        Filiale filiale = (Filiale) o;
        if (filiale.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filiale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filiale{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
