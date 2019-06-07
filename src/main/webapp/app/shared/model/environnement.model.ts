import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { IFiliale } from 'app/shared/model/filiale.model';

export interface IEnvironnement {
    id?: number;
    code?: string;
    libelle?: string;
    champsRecherches?: IChampsRecherche[];
    filiales?: IFiliale[];
}

export class Environnement implements IEnvironnement {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public champsRecherches?: IChampsRecherche[],
        public filiales?: IFiliale[]
    ) {}
}
