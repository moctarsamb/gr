import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

@Component({
    selector: 'jhi-type-extraction-detail',
    templateUrl: './type-extraction-detail.component.html'
})
export class TypeExtractionDetailComponent implements OnInit {
    typeExtraction: ITypeExtraction;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeExtraction }) => {
            this.typeExtraction = typeExtraction;
        });
    }

    previousState() {
        window.history.back();
    }
}
