import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFlux } from 'app/shared/model/flux.model';

@Component({
    selector: 'jhi-flux-detail',
    templateUrl: './flux-detail.component.html'
})
export class FluxDetailComponent implements OnInit {
    flux: IFlux;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ flux }) => {
            this.flux = flux;
        });
    }

    previousState() {
        window.history.back();
    }
}
