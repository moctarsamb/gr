import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultat } from 'app/shared/model/resultat.model';

@Component({
    selector: 'jhi-resultat-detail',
    templateUrl: './resultat-detail.component.html'
})
export class ResultatDetailComponent implements OnInit {
    resultat: IResultat;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultat }) => {
            this.resultat = resultat;
        });
    }

    previousState() {
        window.history.back();
    }
}
