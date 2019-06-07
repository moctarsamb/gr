import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';
import { IResultat } from 'app/shared/model/resultat.model';

export const enum FormatResultat {
    CSV = 'CSV',
    EXCEL = 'EXCEL',
    PDF = 'PDF'
}

export interface IFichierResultat {
    id?: number;
    cheminFichier?: string;
    format?: FormatResultat;
    avecStatistiques?: boolean;
    envoiResultats?: IEnvoiResultat[];
    resultat?: IResultat;
}

export class FichierResultat implements IFichierResultat {
    constructor(
        public id?: number,
        public cheminFichier?: string,
        public format?: FormatResultat,
        public avecStatistiques?: boolean,
        public envoiResultats?: IEnvoiResultat[],
        public resultat?: IResultat
    ) {
        this.avecStatistiques = this.avecStatistiques || false;
    }
}
