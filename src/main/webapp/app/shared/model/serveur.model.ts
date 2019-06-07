export interface IServeur {
    id?: number;
    nom?: string;
    adresseIp?: string;
    login?: string;
    motDePasse?: string;
    repertoireDepots?: string;
    repertoireResultats?: string;
    repertoireParametres?: string;
    estActif?: boolean;
}

export class Serveur implements IServeur {
    constructor(
        public id?: number,
        public nom?: string,
        public adresseIp?: string,
        public login?: string,
        public motDePasse?: string,
        public repertoireDepots?: string,
        public repertoireResultats?: string,
        public repertoireParametres?: string,
        public estActif?: boolean
    ) {
        this.estActif = this.estActif || false;
    }
}
