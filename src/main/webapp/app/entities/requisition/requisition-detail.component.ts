import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequisition } from 'app/shared/model/requisition.model';

@Component({
    selector: 'jhi-requisition-detail',
    templateUrl: './requisition-detail.component.html'
})
export class RequisitionDetailComponent implements OnInit {
    requisition: IRequisition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisition }) => {
            this.requisition = requisition;
        });
    }

    previousState() {
        window.history.back();
    }
}
