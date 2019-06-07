import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';

@Component({
    selector: 'jhi-champs-recherche-detail',
    templateUrl: './champs-recherche-detail.component.html'
})
export class ChampsRechercheDetailComponent implements OnInit {
    champsRecherche: IChampsRecherche;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ champsRecherche }) => {
            this.champsRecherche = champsRecherche;
        });
    }

    previousState() {
        window.history.back();
    }
}
