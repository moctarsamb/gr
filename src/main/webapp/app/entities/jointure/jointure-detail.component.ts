import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJointure } from 'app/shared/model/jointure.model';

@Component({
    selector: 'jhi-jointure-detail',
    templateUrl: './jointure-detail.component.html'
})
export class JointureDetailComponent implements OnInit {
    jointure: IJointure;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jointure }) => {
            this.jointure = jointure;
        });
    }

    previousState() {
        window.history.back();
    }
}
