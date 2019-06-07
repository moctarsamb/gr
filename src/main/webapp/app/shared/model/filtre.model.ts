import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { IClause } from 'app/shared/model/clause.model';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';

export interface IFiltre {
    id?: number;
    typeExtractions?: ITypeExtraction[];
    clauses?: IClause[];
    operateurLogique?: IOperateurLogique;
}

export class Filtre implements IFiltre {
    constructor(
        public id?: number,
        public typeExtractions?: ITypeExtraction[],
        public clauses?: IClause[],
        public operateurLogique?: IOperateurLogique
    ) {}
}
