import { ICritere } from 'app/shared/model/critere.model';
import { IFiltre } from 'app/shared/model/filtre.model';

export interface IOperateurLogique {
    id?: number;
    operateurLogique?: string;
    criteres?: ICritere[];
    filtres?: IFiltre[];
}

export class OperateurLogique implements IOperateurLogique {
    constructor(public id?: number, public operateurLogique?: string, public criteres?: ICritere[], public filtres?: IFiltre[]) {}
}
