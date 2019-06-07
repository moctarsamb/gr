import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { IMonitor } from 'app/shared/model/monitor.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { IFlux } from 'app/shared/model/flux.model';
import { IOperande } from 'app/shared/model/operande.model';
import { IProfil } from 'app/shared/model/profil.model';

export interface IColonne {
    id?: number;
    libelle?: string;
    description?: string;
    alias?: string;
    champsRecherches?: IChampsRecherche[];
    monitors?: IMonitor[];
    typeExtractionRequetees?: ITypeExtraction[];
    flux?: IFlux;
    operandes?: IOperande[];
    profils?: IProfil[];
}

export class Colonne implements IColonne {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public alias?: string,
        public champsRecherches?: IChampsRecherche[],
        public monitors?: IMonitor[],
        public typeExtractionRequetees?: ITypeExtraction[],
        public flux?: IFlux,
        public operandes?: IOperande[],
        public profils?: IProfil[]
    ) {}
}
