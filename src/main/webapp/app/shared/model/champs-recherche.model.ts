import { Moment } from 'moment';
import { IResultat } from 'app/shared/model/resultat.model';
import { IColonne } from 'app/shared/model/colonne.model';
import { IEnvironnement } from 'app/shared/model/environnement.model';
import { IFiliale } from 'app/shared/model/filiale.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

export interface IChampsRecherche {
    id?: number;
    champs?: string;
    dateDebutExtraction?: Moment;
    dateFinExtraction?: Moment;
    resultat?: IResultat;
    colonne?: IColonne;
    environnement?: IEnvironnement;
    filiale?: IFiliale;
    requisition?: IRequisition;
    typeExtraction?: ITypeExtraction;
}

export class ChampsRecherche implements IChampsRecherche {
    constructor(
        public id?: number,
        public champs?: string,
        public dateDebutExtraction?: Moment,
        public dateFinExtraction?: Moment,
        public resultat?: IResultat,
        public colonne?: IColonne,
        public environnement?: IEnvironnement,
        public filiale?: IFiliale,
        public requisition?: IRequisition,
        public typeExtraction?: ITypeExtraction
    ) {}
}
