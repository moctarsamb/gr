export interface IParametrageApplication {
    id?: number;
    nomFichier?: string;
    cheminFichier?: string;
}

export class ParametrageApplication implements IParametrageApplication {
    constructor(public id?: number, public nomFichier?: string, public cheminFichier?: string) {}
}
