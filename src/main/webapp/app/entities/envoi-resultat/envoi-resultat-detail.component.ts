import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';

@Component({
    selector: 'jhi-envoi-resultat-detail',
    templateUrl: './envoi-resultat-detail.component.html'
})
export class EnvoiResultatDetailComponent implements OnInit {
    envoiResultat: IEnvoiResultat;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ envoiResultat }) => {
            this.envoiResultat = envoiResultat;
        });
    }

    previousState() {
        window.history.back();
    }
}
