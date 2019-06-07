import { IJointure } from 'app/shared/model/jointure.model';
import { IClause } from 'app/shared/model/clause.model';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';

export interface ICritere {
    id?: number;
    jointures?: IJointure[];
    clause?: IClause;
    operateurLogique?: IOperateurLogique;
}

export class Critere implements ICritere {
    constructor(public id?: number, public jointures?: IJointure[], public clause?: IClause, public operateurLogique?: IOperateurLogique) {}
}
