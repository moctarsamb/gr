import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFiltre } from 'app/shared/model/filtre.model';

@Component({
    selector: 'jhi-filtre-detail',
    templateUrl: './filtre-detail.component.html'
})
export class FiltreDetailComponent implements OnInit {
    filtre: IFiltre;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filtre }) => {
            this.filtre = filtre;
        });
    }

    previousState() {
        window.history.back();
    }
}
