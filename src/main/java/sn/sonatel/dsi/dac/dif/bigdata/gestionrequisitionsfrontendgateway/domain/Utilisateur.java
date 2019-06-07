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
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false, unique = true)
    private String matricule;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "est_actif", nullable = false)
    private Boolean estActif;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EnvoiResultat> envoiResultats = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();
    @OneToMany(mappedBy = "administrateurProfil")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Profil> profilAdministres = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requisition> requisitions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("utilisateurs")
    private DroitAcces droitAcces;

    @ManyToOne
    @JsonIgnoreProperties("utilisateurs")
    private Filiale filiale;

    @ManyToOne
    @JsonIgnoreProperties("utilisateurs")
    private Profil profil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Utilisateur matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getUsername() {
        return username;
    }

    public Utilisateur username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public Utilisateur firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Utilisateur lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Utilisateur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isEstActif() {
        return estActif;
    }

    public Utilisateur estActif(Boolean estActif) {
        this.estActif = estActif;
        return this;
    }

    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }

    public User getUser() {
        return user;
    }

    public Utilisateur user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<EnvoiResultat> getEnvoiResultats() {
        return envoiResultats;
    }

    public Utilisateur envoiResultats(Set<EnvoiResultat> envoiResultats) {
        this.envoiResultats = envoiResultats;
        return this;
    }

    public Utilisateur addEnvoiResultat(EnvoiResultat envoiResultat) {
        this.envoiResultats.add(envoiResultat);
        envoiResultat.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeEnvoiResultat(EnvoiResultat envoiResultat) {
        this.envoiResultats.remove(envoiResultat);
        envoiResultat.setUtilisateur(null);
        return this;
    }

    public void setEnvoiResultats(Set<EnvoiResultat> envoiResultats) {
        this.envoiResultats = envoiResultats;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Utilisateur notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Utilisateur addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setUtilisateur(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Profil> getProfilAdministres() {
        return profilAdministres;
    }

    public Utilisateur profilAdministres(Set<Profil> profils) {
        this.profilAdministres = profils;
        return this;
    }

    public Utilisateur addProfilAdministre(Profil profil) {
        this.profilAdministres.add(profil);
        profil.setAdministrateurProfil(this);
        return this;
    }

    public Utilisateur removeProfilAdministre(Profil profil) {
        this.profilAdministres.remove(profil);
        profil.setAdministrateurProfil(null);
        return this;
    }

    public void setProfilAdministres(Set<Profil> profils) {
        this.profilAdministres = profils;
    }

    public Set<Requisition> getRequisitions() {
        return requisitions;
    }

    public Utilisateur requisitions(Set<Requisition> requisitions) {
        this.requisitions = requisitions;
        return this;
    }

    public Utilisateur addRequisition(Requisition requisition) {
        this.requisitions.add(requisition);
        requisition.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeRequisition(Requisition requisition) {
        this.requisitions.remove(requisition);
        requisition.setUtilisateur(null);
        return this;
    }

    public void setRequisitions(Set<Requisition> requisitions) {
        this.requisitions = requisitions;
    }

    public DroitAcces getDroitAcces() {
        return droitAcces;
    }

    public Utilisateur droitAcces(DroitAcces droitAcces) {
        this.droitAcces = droitAcces;
        return this;
    }

    public void setDroitAcces(DroitAcces droitAcces) {
        this.droitAcces = droitAcces;
    }

    public Filiale getFiliale() {
        return filiale;
    }

    public Utilisateur filiale(Filiale filiale) {
        this.filiale = filiale;
        return this;
    }

    public void setFiliale(Filiale filiale) {
        this.filiale = filiale;
    }

    public Profil getProfil() {
        return profil;
    }

    public Utilisateur profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
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
        Utilisateur utilisateur = (Utilisateur) o;
        if (utilisateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), utilisateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", username='" + getUsername() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", estActif='" + isEstActif() + "'" +
            "}";
    }
}
