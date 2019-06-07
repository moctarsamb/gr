import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from './dimension.service';
import { IMonitor } from 'app/shared/model/monitor.model';
import { MonitorService } from 'app/entities/monitor';
import { IBase } from 'app/shared/model/base.model';
import { BaseService } from 'app/entities/base';
import { IFlux } from 'app/shared/model/flux.model';
import { FluxService } from 'app/entities/flux';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from 'app/entities/type-extraction';

@Component({
    selector: 'jhi-dimension-update',
    templateUrl: './dimension-update.component.html'
})
export class DimensionUpdateComponent implements OnInit {
    dimension: IDimension;
    isSaving: boolean;

    monitors: IMonitor[];

    bases: IBase[];

    fluxes: IFlux[];

    typeextractions: ITypeExtraction[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dimensionService: DimensionService,
        protected monitorService: MonitorService,
        protected baseService: BaseService,
        protected fluxService: FluxService,
        protected typeExtractionService: TypeExtractionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dimension }) => {
            this.dimension = dimension;
        });
        this.monitorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMonitor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMonitor[]>) => response.body)
            )
            .subscribe((res: IMonitor[]) => (this.monitors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.baseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBase[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBase[]>) => response.body)
            )
            .subscribe((res: IBase[]) => (this.bases = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.fluxService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFlux[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFlux[]>) => response.body)
            )
            .subscribe((res: IFlux[]) => (this.fluxes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeExtractionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExtraction[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExtraction[]>) => response.body)
            )
            .subscribe((res: ITypeExtraction[]) => (this.typeextractions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dimension.id !== undefined) {
            this.subscribeToSaveResponse(this.dimensionService.update(this.dimension));
        } else {
            this.subscribeToSaveResponse(this.dimensionService.create(this.dimension));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDimension>>) {
        result.subscribe((res: HttpResponse<IDimension>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMonitorById(index: number, item: IMonitor) {
        return item.id;
    }

    trackBaseById(index: number, item: IBase) {
        return item.id;
    }

    trackFluxById(index: number, item: IFlux) {
        return item.id;
    }

    trackTypeExtractionById(index: number, item: ITypeExtraction) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
