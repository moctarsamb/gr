package sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.StatusEvolution;

import sn.sonatel.dsi.dac.dif.bigdata.gestionrequisitionsfrontendgateway.domain.enumeration.EtatRequisition;

/**
 * A Requisition.
 */
@Entity
@Table(name = "requisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "requisition")
public class Requisition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_arrivee_demande", nullable = false)
    private Integer numeroArriveeDemande;

    @NotNull
    @Column(name = "numero_pv", nullable = false, unique = true)
    private Integer numeroPv;

    @NotNull
    @Column(name = "date_saisie_pv", nullable = false)
    private LocalDate dateSaisiePv;

    @NotNull
    @Column(name = "date_arrivee_demande", nullable = false)
    private Instant dateArriveeDemande;

    @NotNull
    @Column(name = "date_saisie_demande", nullable = false)
    private Instant dateSaisieDemande;

    @NotNull
    @Column(name = "envoi_resultat_automatique", nullable = false)
    private Boolean envoiResultatAutomatique;

    @Column(name = "date_reponse")
    private Instant dateReponse;

    @Column(name = "date_renvoie_resultat")
    private Instant dateRenvoieResultat;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEvolution status;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatRequisition etat;

    @OneToMany(mappedBy = "requisition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChampsRecherche> champsRecherches = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private Provenance provenance;

    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private Structure structure;

    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private Utilisateur utilisateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroArriveeDemande() {
        return numeroArriveeDemande;
    }

    public Requisition numeroArriveeDemande(Integer numeroArriveeDemande) {
        this.numeroArriveeDemande = numeroArriveeDemande;
        return this;
    }

    public void setNumeroArriveeDemande(Integer numeroArriveeDemande) {
        this.numeroArriveeDemande = numeroArriveeDemande;
    }

    public Integer getNumeroPv() {
        return numeroPv;
    }

    public Requisition numeroPv(Integer numeroPv) {
        this.numeroPv = numeroPv;
        return this;
    }

    public void setNumeroPv(Integer numeroPv) {
        this.numeroPv = numeroPv;
    }

    public LocalDate getDateSaisiePv() {
        return dateSaisiePv;
    }

    public Requisition dateSaisiePv(LocalDate dateSaisiePv) {
        this.dateSaisiePv = dateSaisiePv;
        return this;
    }

    public void setDateSaisiePv(LocalDate dateSaisiePv) {
        this.dateSaisiePv = dateSaisiePv;
    }

    public Instant getDateArriveeDemande() {
        return dateArriveeDemande;
    }

    public Requisition dateArriveeDemande(Instant dateArriveeDemande) {
        this.dateArriveeDemande = dateArriveeDemande;
        return this;
    }

    public void setDateArriveeDemande(Instant dateArriveeDemande) {
        this.dateArriveeDemande = dateArriveeDemande;
    }

    public Instant getDateSaisieDemande() {
        return dateSaisieDemande;
    }

    public Requisition dateSaisieDemande(Instant dateSaisieDemande) {
        this.dateSaisieDemande = dateSaisieDemande;
        return this;
    }

    public void setDateSaisieDemande(Instant dateSaisieDemande) {
        this.dateSaisieDemande = dateSaisieDemande;
    }

    public Boolean isEnvoiResultatAutomatique() {
        return envoiResultatAutomatique;
    }

    public Requisition envoiResultatAutomatique(Boolean envoiResultatAutomatique) {
        this.envoiResultatAutomatique = envoiResultatAutomatique;
        return this;
    }

    public void setEnvoiResultatAutomatique(Boolean envoiResultatAutomatique) {
        this.envoiResultatAutomatique = envoiResultatAutomatique;
    }

    public Instant getDateReponse() {
        return dateReponse;
    }

    public Requisition dateReponse(Instant dateReponse) {
        this.dateReponse = dateReponse;
        return this;
    }

    public void setDateReponse(Instant dateReponse) {
        this.dateReponse = dateReponse;
    }

    public Instant getDateRenvoieResultat() {
        return dateRenvoieResultat;
    }

    public Requisition dateRenvoieResultat(Instant dateRenvoieResultat) {
        this.dateRenvoieResultat = dateRenvoieResultat;
        return this;
    }

    public void setDateRenvoieResultat(Instant dateRenvoieResultat) {
        this.dateRenvoieResultat = dateRenvoieResultat;
    }

    public StatusEvolution getStatus() {
        return status;
    }

    public Requisition status(StatusEvolution status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEvolution status) {
        this.status = status;
    }

    public EtatRequisition getEtat() {
        return etat;
    }

    public Requisition etat(EtatRequisition etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatRequisition etat) {
        this.etat = etat;
    }

    public Set<ChampsRecherche> getChampsRecherches() {
        return champsRecherches;
    }

    public Requisition champsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
        return this;
    }

    public Requisition addChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.add(champsRecherche);
        champsRecherche.setRequisition(this);
        return this;
    }

    public Requisition removeChampsRecherche(ChampsRecherche champsRecherche) {
        this.champsRecherches.remove(champsRecherche);
        champsRecherche.setRequisition(null);
        return this;
    }

    public void setChampsRecherches(Set<ChampsRecherche> champsRecherches) {
        this.champsRecherches = champsRecherches;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public Requisition provenance(Provenance provenance) {
        this.provenance = provenance;
        return this;
    }

    public void setProvenance(Provenance provenance) {
        this.provenance = provenance;
    }

    public Structure getStructure() {
        return structure;
    }

    public Requisition structure(Structure structure) {
        this.structure = structure;
        return this;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Requisition utilisateur(Utilisateur utilisateur) {
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
        Requisition requisition = (Requisition) o;
        if (requisition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Requisition{" +
            "id=" + getId() +
            ", numeroArriveeDemande=" + getNumeroArriveeDemande() +
            ", numeroPv=" + getNumeroPv() +
            ", dateSaisiePv='" + getDateSaisiePv() + "'" +
            ", dateArriveeDemande='" + getDateArriveeDemande() + "'" +
            ", dateSaisieDemande='" + getDateSaisieDemande() + "'" +
            ", envoiResultatAutomatique='" + isEnvoiResultatAutomatique() + "'" +
            ", dateReponse='" + getDateReponse() + "'" +
            ", dateRenvoieResultat='" + getDateRenvoieResultat() + "'" +
            ", status='" + getStatus() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
