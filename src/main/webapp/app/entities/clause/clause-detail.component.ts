import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClause } from 'app/shared/model/clause.model';

@Component({
    selector: 'jhi-clause-detail',
    templateUrl: './clause-detail.component.html'
})
export class ClauseDetailComponent implements OnInit {
    clause: IClause;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clause }) => {
            this.clause = clause;
        });
    }

    previousState() {
        window.history.back();
    }
}
