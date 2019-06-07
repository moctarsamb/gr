import { IMonitor } from 'app/shared/model/monitor.model';
import { IOperande } from 'app/shared/model/operande.model';

export interface IFonction {
    id?: number;
    nom?: string;
    monitors?: IMonitor[];
    operandes?: IOperande[];
}

export class Fonction implements IFonction {
    constructor(public id?: number, public nom?: string, public monitors?: IMonitor[], public operandes?: IOperande[]) {}
}
