import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProvenance } from 'app/shared/model/provenance.model';
import { ProvenanceService } from './provenance.service';

@Component({
    selector: 'jhi-provenance-update',
    templateUrl: './provenance-update.component.html'
})
export class ProvenanceUpdateComponent implements OnInit {
    provenance: IProvenance;
    isSaving: boolean;

    constructor(protected provenanceService: ProvenanceService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ provenance }) => {
            this.provenance = provenance;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.provenance.id !== undefined) {
            this.subscribeToSaveResponse(this.provenanceService.update(this.provenance));
        } else {
            this.subscribeToSaveResponse(this.provenanceService.create(this.provenance));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvenance>>) {
        result.subscribe((res: HttpResponse<IProvenance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
