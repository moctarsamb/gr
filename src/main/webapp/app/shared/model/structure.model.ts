import { IRequisition } from 'app/shared/model/requisition.model';
import { IFiliale } from 'app/shared/model/filiale.model';

export interface IStructure {
    id?: number;
    code?: string;
    libelle?: string;
    requisitions?: IRequisition[];
    filiale?: IFiliale;
}

export class Structure implements IStructure {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public requisitions?: IRequisition[],
        public filiale?: IFiliale
    ) {}
}
