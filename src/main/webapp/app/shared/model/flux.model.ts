import { IColonne } from 'app/shared/model/colonne.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IJointure } from 'app/shared/model/jointure.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { IBase } from 'app/shared/model/base.model';

export interface IFlux {
    id?: number;
    libelle?: string;
    description?: string;
    colonnes?: IColonne[];
    dimensions?: IDimension[];
    jointures?: IJointure[];
    typeExtractions?: ITypeExtraction[];
    base?: IBase;
}

export class Flux implements IFlux {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public colonnes?: IColonne[],
        public dimensions?: IDimension[],
        public jointures?: IJointure[],
        public typeExtractions?: ITypeExtraction[],
        public base?: IBase
    ) {}
}
