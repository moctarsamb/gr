import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { IColonne } from 'app/shared/model/colonne.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { IFiliale } from 'app/shared/model/filiale.model';

export interface IProfil {
    id?: number;
    libelle?: string;
    description?: string;
    utilisateurs?: IUtilisateur[];
    colonnes?: IColonne[];
    typeExtractions?: ITypeExtraction[];
    administrateurProfil?: IUtilisateur;
    filiales?: IFiliale[];
}

export class Profil implements IProfil {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public utilisateurs?: IUtilisateur[],
        public colonnes?: IColonne[],
        public typeExtractions?: ITypeExtraction[],
        public administrateurProfil?: IUtilisateur,
        public filiales?: IFiliale[]
    ) {}
}
