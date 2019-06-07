import { IRequisition } from 'app/shared/model/requisition.model';

export interface IProvenance {
    id?: number;
    code?: string;
    libelle?: string;
    email?: string;
    requisitions?: IRequisition[];
}

export class Provenance implements IProvenance {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public email?: string,
        public requisitions?: IRequisition[]
    ) {}
}
