import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFlux } from 'app/shared/model/flux.model';
import { FluxService } from './flux.service';
import { IBase } from 'app/shared/model/base.model';
import { BaseService } from 'app/entities/base';

@Component({
    selector: 'jhi-flux-update',
    templateUrl: './flux-update.component.html'
})
export class FluxUpdateComponent implements OnInit {
    flux: IFlux;
    isSaving: boolean;

    bases: IBase[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected fluxService: FluxService,
        protected baseService: BaseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ flux }) => {
            this.flux = flux;
        });
        this.baseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBase[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBase[]>) => response.body)
            )
            .subscribe((res: IBase[]) => (this.bases = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.flux.id !== undefined) {
            this.subscribeToSaveResponse(this.fluxService.update(this.flux));
        } else {
            this.subscribeToSaveResponse(this.fluxService.create(this.flux));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlux>>) {
        result.subscribe((res: HttpResponse<IFlux>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBaseById(index: number, item: IBase) {
        return item.id;
    }
}
