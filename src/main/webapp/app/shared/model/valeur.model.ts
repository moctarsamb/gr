import { IOperande } from 'app/shared/model/operande.model';

export interface IValeur {
    id?: number;
    valeur?: string;
    operandes?: IOperande[];
}

export class Valeur implements IValeur {
    constructor(public id?: number, public valeur?: string, public operandes?: IOperande[]) {}
}
