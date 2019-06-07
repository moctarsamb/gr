import { IDimension } from 'app/shared/model/dimension.model';
import { IFlux } from 'app/shared/model/flux.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

export interface IBase {
    id?: number;
    libelle?: string;
    description?: string;
    dimensions?: IDimension[];
    fluxes?: IFlux[];
    typeExtractions?: ITypeExtraction[];
}

export class Base implements IBase {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public dimensions?: IDimension[],
        public fluxes?: IFlux[],
        public typeExtractions?: ITypeExtraction[]
    ) {}
}
