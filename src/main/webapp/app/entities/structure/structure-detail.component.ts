import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStructure } from 'app/shared/model/structure.model';

@Component({
    selector: 'jhi-structure-detail',
    templateUrl: './structure-detail.component.html'
})
export class StructureDetailComponent implements OnInit {
    structure: IStructure;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ structure }) => {
            this.structure = structure;
        });
    }

    previousState() {
        window.history.back();
    }
}
