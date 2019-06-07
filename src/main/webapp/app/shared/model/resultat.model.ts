import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';

export const enum StatusEvolution {
    ENCOURS = 'ENCOURS',
    TERMINE = 'TERMINE'
}

export const enum EtatResultat {
    SUCCES = 'SUCCES',
    SANSREPONSE = 'SANSREPONSE',
    CHINOIS = 'CHINOIS',
    ERREUR = 'ERREUR',
    ERREURBD = 'ERREURBD'
}

export interface IResultat {
    id?: number;
    status?: StatusEvolution;
    etat?: EtatResultat;
    champsRecherche?: IChampsRecherche;
    fichierResultats?: IFichierResultat[];
}

export class Resultat implements IResultat {
    constructor(
        public id?: number,
        public status?: StatusEvolution,
        public etat?: EtatResultat,
        public champsRecherche?: IChampsRecherche,
        public fichierResultats?: IFichierResultat[]
    ) {}
}
