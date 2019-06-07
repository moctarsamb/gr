import { Moment } from 'moment';
import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IEnvoiResultat {
    id?: number;
    email?: string;
    dateEnvoi?: Moment;
    fichierResultat?: IFichierResultat;
    utilisateur?: IUtilisateur;
}

export class EnvoiResultat implements IEnvoiResultat {
    constructor(
        public id?: number,
        public email?: string,
        public dateEnvoi?: Moment,
        public fichierResultat?: IFichierResultat,
        public utilisateur?: IUtilisateur
    ) {}
}
