import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDroitAcces } from 'app/shared/model/droit-acces.model';

@Component({
    selector: 'jhi-droit-acces-detail',
    templateUrl: './droit-acces-detail.component.html'
})
export class DroitAccesDetailComponent implements OnInit {
    droitAcces: IDroitAcces;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ droitAcces }) => {
            this.droitAcces = droitAcces;
        });
    }

    previousState() {
        window.history.back();
    }
}
