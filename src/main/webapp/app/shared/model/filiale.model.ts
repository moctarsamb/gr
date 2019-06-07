import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { IStructure } from 'app/shared/model/structure.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { IEnvironnement } from 'app/shared/model/environnement.model';
import { IProfil } from 'app/shared/model/profil.model';

export interface IFiliale {
    id?: number;
    code?: string;
    libelle?: string;
    champsRecherches?: IChampsRecherche[];
    structures?: IStructure[];
    utilisateurs?: IUtilisateur[];
    environnements?: IEnvironnement[];
    profils?: IProfil[];
}

export class Filiale implements IFiliale {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public champsRecherches?: IChampsRecherche[],
        public structures?: IStructure[],
        public utilisateurs?: IUtilisateur[],
        public environnements?: IEnvironnement[],
        public profils?: IProfil[]
    ) {}
}
