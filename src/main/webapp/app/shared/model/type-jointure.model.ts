import { IJointure } from 'app/shared/model/jointure.model';

export interface ITypeJointure {
    id?: number;
    type?: string;
    jointures?: IJointure[];
}

export class TypeJointure implements ITypeJointure {
    constructor(public id?: number, public type?: string, public jointures?: IJointure[]) {}
}
