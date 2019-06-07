import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFiliale } from 'app/shared/model/filiale.model';

@Component({
    selector: 'jhi-filiale-detail',
    templateUrl: './filiale-detail.component.html'
})
export class FilialeDetailComponent implements OnInit {
    filiale: IFiliale;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filiale }) => {
            this.filiale = filiale;
        });
    }

    previousState() {
        window.history.back();
    }
}
