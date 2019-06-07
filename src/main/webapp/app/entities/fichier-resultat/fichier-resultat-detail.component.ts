import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';

@Component({
    selector: 'jhi-fichier-resultat-detail',
    templateUrl: './fichier-resultat-detail.component.html'
})
export class FichierResultatDetailComponent implements OnInit {
    fichierResultat: IFichierResultat;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fichierResultat }) => {
            this.fichierResultat = fichierResultat;
        });
    }

    previousState() {
        window.history.back();
    }
}
