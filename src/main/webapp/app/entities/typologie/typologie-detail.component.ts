import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypologie } from 'app/shared/model/typologie.model';

@Component({
    selector: 'jhi-typologie-detail',
    templateUrl: './typologie-detail.component.html'
})
export class TypologieDetailComponent implements OnInit {
    typologie: ITypologie;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typologie }) => {
            this.typologie = typologie;
        });
    }

    previousState() {
        window.history.back();
    }
}
