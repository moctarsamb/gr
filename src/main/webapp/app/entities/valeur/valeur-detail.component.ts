import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValeur } from 'app/shared/model/valeur.model';

@Component({
    selector: 'jhi-valeur-detail',
    templateUrl: './valeur-detail.component.html'
})
export class ValeurDetailComponent implements OnInit {
    valeur: IValeur;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ valeur }) => {
            this.valeur = valeur;
        });
    }

    previousState() {
        window.history.back();
    }
}
