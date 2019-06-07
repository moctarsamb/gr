import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProvenance } from 'app/shared/model/provenance.model';

@Component({
    selector: 'jhi-provenance-detail',
    templateUrl: './provenance-detail.component.html'
})
export class ProvenanceDetailComponent implements OnInit {
    provenance: IProvenance;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ provenance }) => {
            this.provenance = provenance;
        });
    }

    previousState() {
        window.history.back();
    }
}
