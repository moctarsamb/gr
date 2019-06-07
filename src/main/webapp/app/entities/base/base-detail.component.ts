import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBase } from 'app/shared/model/base.model';

@Component({
    selector: 'jhi-base-detail',
    templateUrl: './base-detail.component.html'
})
export class BaseDetailComponent implements OnInit {
    base: IBase;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ base }) => {
            this.base = base;
        });
    }

    previousState() {
        window.history.back();
    }
}
