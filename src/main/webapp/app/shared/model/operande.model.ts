import { IColonne } from 'app/shared/model/colonne.model';
import { IValeur } from 'app/shared/model/valeur.model';
import { IFonction } from 'app/shared/model/fonction.model';
import { IClause } from 'app/shared/model/clause.model';

export interface IOperande {
    id?: number;
    colonnes?: IColonne[];
    valeurs?: IValeur[];
    fonction?: IFonction;
    clauses?: IClause[];
}

export class Operande implements IOperande {
    constructor(
        public id?: number,
        public colonnes?: IColonne[],
        public valeurs?: IValeur[],
        public fonction?: IFonction,
        public clauses?: IClause[]
    ) {}
}
