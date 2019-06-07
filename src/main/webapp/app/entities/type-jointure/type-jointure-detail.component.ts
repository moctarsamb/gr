import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeJointure } from 'app/shared/model/type-jointure.model';

@Component({
    selector: 'jhi-type-jointure-detail',
    templateUrl: './type-jointure-detail.component.html'
})
export class TypeJointureDetailComponent implements OnInit {
    typeJointure: ITypeJointure;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeJointure }) => {
            this.typeJointure = typeJointure;
        });
    }

    previousState() {
        window.history.back();
    }
}
