import { ICritere } from 'app/shared/model/critere.model';
import { IFlux } from 'app/shared/model/flux.model';
import { ITypeJointure } from 'app/shared/model/type-jointure.model';

export interface IJointure {
    id?: number;
    critere?: ICritere;
    flux?: IFlux;
    typeJointure?: ITypeJointure;
}

export class Jointure implements IJointure {
    constructor(public id?: number, public critere?: ICritere, public flux?: IFlux, public typeJointure?: ITypeJointure) {}
}
