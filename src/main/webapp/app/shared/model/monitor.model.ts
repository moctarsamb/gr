import { IColonne } from 'app/shared/model/colonne.model';
import { IFonction } from 'app/shared/model/fonction.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

export const enum TypeMonitor {
    COLONNE = 'COLONNE',
    FONCTION = 'FONCTION'
}

export interface IMonitor {
    id?: number;
    type?: TypeMonitor;
    colonne?: IColonne;
    fonction?: IFonction;
    dimensions?: IDimension[];
    typeExtractions?: ITypeExtraction[];
}

export class Monitor implements IMonitor {
    constructor(
        public id?: number,
        public type?: TypeMonitor,
        public colonne?: IColonne,
        public fonction?: IFonction,
        public dimensions?: IDimension[],
        public typeExtractions?: ITypeExtraction[]
    ) {}
}
