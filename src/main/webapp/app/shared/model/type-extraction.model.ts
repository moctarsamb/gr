import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IMonitor } from 'app/shared/model/monitor.model';
import { IBase } from 'app/shared/model/base.model';
import { IFiltre } from 'app/shared/model/filtre.model';
import { IFlux } from 'app/shared/model/flux.model';
import { IColonne } from 'app/shared/model/colonne.model';
import { IProfil } from 'app/shared/model/profil.model';

export interface ITypeExtraction {
    id?: number;
    code?: string;
    libelle?: string;
    champsRecherches?: IChampsRecherche[];
    dimensions?: IDimension[];
    monitors?: IMonitor[];
    base?: IBase;
    filtre?: IFiltre;
    flux?: IFlux;
    colonneRequetables?: IColonne[];
    profils?: IProfil[];
}

export class TypeExtraction implements ITypeExtraction {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public champsRecherches?: IChampsRecherche[],
        public dimensions?: IDimension[],
        public monitors?: IMonitor[],
        public base?: IBase,
        public filtre?: IFiltre,
        public flux?: IFlux,
        public colonneRequetables?: IColonne[],
        public profils?: IProfil[]
    ) {}
}
