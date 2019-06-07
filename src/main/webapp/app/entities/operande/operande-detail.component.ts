import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperande } from 'app/shared/model/operande.model';

@Component({
    selector: 'jhi-operande-detail',
    templateUrl: './operande-detail.component.html'
})
export class OperandeDetailComponent implements OnInit {
    operande: IOperande;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operande }) => {
            this.operande = operande;
        });
    }

    previousState() {
        window.history.back();
    }
}
