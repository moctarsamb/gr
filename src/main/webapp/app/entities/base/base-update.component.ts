import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IBase } from 'app/shared/model/base.model';
import { BaseService } from './base.service';

@Component({
    selector: 'jhi-base-update',
    templateUrl: './base-update.component.html'
})
export class BaseUpdateComponent implements OnInit {
    base: IBase;
    isSaving: boolean;

    constructor(protected baseService: BaseService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ base }) => {
            this.base = base;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.base.id !== undefined) {
            this.subscribeToSaveResponse(this.baseService.update(this.base));
        } else {
            this.subscribeToSaveResponse(this.baseService.create(this.base));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBase>>) {
        result.subscribe((res: HttpResponse<IBase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
