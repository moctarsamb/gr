package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A EnvoiResultat.
 */
@Entity
@Table(name = "envoi_resultat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "envoiresultat")
public class EnvoiResultat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_envoi")
    private Instant dateEnvoi;

    @ManyToOne
    @JsonIgnoreProperties("envoiResultats")
    private FichierResultat fichierResultat;

    @ManyToOne
    @JsonIgnoreProperties("envoiResultats")
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public EnvoiResultat email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDateEnvoi() {
        return dateEnvoi;
    }

    public EnvoiResultat dateEnvoi(Instant dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
        return this;
    }

    public void setDateEnvoi(Instant dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public FichierResultat getFichierResultat() {
        return fichierResultat;
    }

    public EnvoiResultat fichierResultat(FichierResultat fichierResultat) {
        this.fichierResultat = fichierResultat;
        return this;
    }

    public void setFichierResultat(FichierResultat fichierResultat) {
        this.fichierResultat = fichierResultat;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public EnvoiResultat utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
        EnvoiResultat envoiResultat = (EnvoiResultat) o;
        if (envoiResultat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), envoiResultat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnvoiResultat{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", dateEnvoi='" + getDateEnvoi() + "'" +
            "}";
    }
}
