import { IClause } from 'app/shared/model/clause.model';

export interface IOperateur {
    id?: number;
    operateur?: string;
    clauses?: IClause[];
}

export class Operateur implements IOperateur {
    constructor(public id?: number, public operateur?: string, public clauses?: IClause[]) {}
}
