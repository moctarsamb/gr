import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParametrageApplication } from 'app/shared/model/parametrage-application.model';

@Component({
    selector: 'jhi-parametrage-application-detail',
    templateUrl: './parametrage-application-detail.component.html'
})
export class ParametrageApplicationDetailComponent implements OnInit {
    parametrageApplication: IParametrageApplication;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parametrageApplication }) => {
            this.parametrageApplication = parametrageApplication;
        });
    }

    previousState() {
        window.history.back();
    }
}
