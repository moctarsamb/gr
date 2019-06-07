import { ICritere } from 'app/shared/model/critere.model';
import { IOperande } from 'app/shared/model/operande.model';
import { IOperateur } from 'app/shared/model/operateur.model';
import { IFiltre } from 'app/shared/model/filtre.model';

export interface IClause {
    id?: number;
    prefixe?: string;
    suffixe?: string;
    criteres?: ICritere[];
    operandes?: IOperande[];
    operateur?: IOperateur;
    filtres?: IFiltre[];
}

export class Clause implements IClause {
    constructor(
        public id?: number,
        public prefixe?: string,
        public suffixe?: string,
        public criteres?: ICritere[],
        public operandes?: IOperande[],
        public operateur?: IOperateur,
        public filtres?: IFiltre[]
    ) {}
}
