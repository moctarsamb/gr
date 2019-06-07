import { Moment } from 'moment';
import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { IProvenance } from 'app/shared/model/provenance.model';
import { IStructure } from 'app/shared/model/structure.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export const enum StatusEvolution {
    ENCOURS = 'ENCOURS',
    TERMINE = 'TERMINE'
}

export const enum EtatRequisition {
    ERREUR = 'ERREUR',
    SUCCES = 'SUCCES'
}

export interface IRequisition {
    id?: number;
    numeroArriveeDemande?: number;
    numeroPv?: number;
    dateSaisiePv?: Moment;
    dateArriveeDemande?: Moment;
    dateSaisieDemande?: Moment;
    envoiResultatAutomatique?: boolean;
    dateReponse?: Moment;
    dateRenvoieResultat?: Moment;
    status?: StatusEvolution;
    etat?: EtatRequisition;
    champsRecherches?: IChampsRecherche[];
    provenance?: IProvenance;
    structure?: IStructure;
    utilisateur?: IUtilisateur;
}

export class Requisition implements IRequisition {
    constructor(
        public id?: number,
        public numeroArriveeDemande?: number,
        public numeroPv?: number,
        public dateSaisiePv?: Moment,
        public dateArriveeDemande?: Moment,
        public dateSaisieDemande?: Moment,
        public envoiResultatAutomatique?: boolean,
        public dateReponse?: Moment,
        public dateRenvoieResultat?: Moment,
        public status?: StatusEvolution,
        public etat?: EtatRequisition,
        public champsRecherches?: IChampsRecherche[],
        public provenance?: IProvenance,
        public structure?: IStructure,
        public utilisateur?: IUtilisateur
    ) {
        this.envoiResultatAutomatique = this.envoiResultatAutomatique || false;
    }
}
