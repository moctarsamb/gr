import { IUser } from 'app/core/user/user.model';
import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';
import { INotification } from 'app/shared/model/notification.model';
import { IProfil } from 'app/shared/model/profil.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { IDroitAcces } from 'app/shared/model/droit-acces.model';
import { IFiliale } from 'app/shared/model/filiale.model';

export interface IUtilisateur {
    id?: number;
    matricule?: string;
    username?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    estActif?: boolean;
    user?: IUser;
    envoiResultats?: IEnvoiResultat[];
    notifications?: INotification[];
    profilAdministres?: IProfil[];
    requisitions?: IRequisition[];
    droitAcces?: IDroitAcces;
    filiale?: IFiliale;
    profil?: IProfil;
}

export class Utilisateur implements IUtilisateur {
    constructor(
        public id?: number,
        public matricule?: string,
        public username?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public estActif?: boolean,
        public user?: IUser,
        public envoiResultats?: IEnvoiResultat[],
        public notifications?: INotification[],
        public profilAdministres?: IProfil[],
        public requisitions?: IRequisition[],
        public droitAcces?: IDroitAcces,
        public filiale?: IFiliale,
        public profil?: IProfil
    ) {
        this.estActif = this.estActif || false;
    }
}
