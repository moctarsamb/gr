import { IMonitor } from 'app/shared/model/monitor.model';
import { IBase } from 'app/shared/model/base.model';
import { IFlux } from 'app/shared/model/flux.model';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

export interface IDimension {
    id?: number;
    monitors?: IMonitor[];
    base?: IBase;
    flux?: IFlux;
    typeExtraction?: ITypeExtraction;
}

export class Dimension implements IDimension {
    constructor(
        public id?: number,
        public monitors?: IMonitor[],
        public base?: IBase,
        public flux?: IFlux,
        public typeExtraction?: ITypeExtraction
    ) {}
}
