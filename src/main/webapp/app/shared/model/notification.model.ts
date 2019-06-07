import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface INotification {
    id?: number;
    libelle?: string;
    description?: string;
    estOuvert?: boolean;
    utilisateur?: IUtilisateur;
}

export class Notification implements INotification {
    constructor(
        public id?: number,
        public libelle?: string,
        public description?: string,
        public estOuvert?: boolean,
        public utilisateur?: IUtilisateur
    ) {
        this.estOuvert = this.estOuvert || false;
    }
}
