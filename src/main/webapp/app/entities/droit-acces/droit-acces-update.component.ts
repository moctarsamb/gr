import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDroitAcces } from 'app/shared/model/droit-acces.model';
import { DroitAccesService } from './droit-acces.service';

@Component({
    selector: 'jhi-droit-acces-update',
    templateUrl: './droit-acces-update.component.html'
})
export class DroitAccesUpdateComponent implements OnInit {
    droitAcces: IDroitAcces;
    isSaving: boolean;

    constructor(protected droitAccesService: DroitAccesService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ droitAcces }) => {
            this.droitAcces = droitAcces;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.droitAcces.id !== undefined) {
            this.subscribeToSaveResponse(this.droitAccesService.update(this.droitAcces));
        } else {
            this.subscribeToSaveResponse(this.droitAccesService.create(this.droitAcces));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDroitAcces>>) {
        result.subscribe((res: HttpResponse<IDroitAcces>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
