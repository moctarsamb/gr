export interface ITypologie {
    id?: number;
    code?: string;
    libelle?: string;
    condition?: string;
    traitement?: string;
}

export class Typologie implements ITypologie {
    constructor(public id?: number, public code?: string, public libelle?: string, public condition?: string, public traitement?: string) {}
}
