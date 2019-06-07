import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICritere } from 'app/shared/model/critere.model';

@Component({
    selector: 'jhi-critere-detail',
    templateUrl: './critere-detail.component.html'
})
export class CritereDetailComponent implements OnInit {
    critere: ICritere;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ critere }) => {
            this.critere = critere;
        });
    }

    previousState() {
        window.history.back();
    }
}
