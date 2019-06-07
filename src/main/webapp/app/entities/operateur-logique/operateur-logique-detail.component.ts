import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';

@Component({
    selector: 'jhi-operateur-logique-detail',
    templateUrl: './operateur-logique-detail.component.html'
})
export class OperateurLogiqueDetailComponent implements OnInit {
    operateurLogique: IOperateurLogique;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operateurLogique }) => {
            this.operateurLogique = operateurLogique;
        });
    }

    previousState() {
        window.history.back();
    }
}
