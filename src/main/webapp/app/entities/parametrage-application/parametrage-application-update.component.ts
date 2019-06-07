import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IParametrageApplication } from 'app/shared/model/parametrage-application.model';
import { ParametrageApplicationService } from './parametrage-application.service';

@Component({
    selector: 'jhi-parametrage-application-update',
    templateUrl: './parametrage-application-update.component.html'
})
export class ParametrageApplicationUpdateComponent implements OnInit {
    parametrageApplication: IParametrageApplication;
    isSaving: boolean;

    constructor(protected parametrageApplicationService: ParametrageApplicationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parametrageApplication }) => {
            this.parametrageApplication = parametrageApplication;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parametrageApplication.id !== undefined) {
            this.subscribeToSaveResponse(this.parametrageApplicationService.update(this.parametrageApplication));
        } else {
            this.subscribeToSaveResponse(this.parametrageApplicationService.create(this.parametrageApplication));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IParametrageApplication>>) {
        result.subscribe(
            (res: HttpResponse<IParametrageApplication>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
