import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IDroitAcces {
    id?: number;
    code?: string;
    libelle?: string;
    utilisateurs?: IUtilisateur[];
}

export class DroitAcces implements IDroitAcces {
    constructor(public id?: number, public code?: string, public libelle?: string, public utilisateurs?: IUtilisateur[]) {}
}
