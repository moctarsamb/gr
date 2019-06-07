import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMonitor } from 'app/shared/model/monitor.model';
import { MonitorService } from './monitor.service';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from 'app/entities/colonne';
import { IFonction } from 'app/shared/model/fonction.model';
import { FonctionService } from 'app/entities/fonction';
import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from 'app/entities/dimension';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from 'app/entities/type-extraction';

@Component({
    selector: 'jhi-monitor-update',
    templateUrl: './monitor-update.component.html'
})
export class MonitorUpdateComponent implements OnInit {
    monitor: IMonitor;
    isSaving: boolean;

    colonnes: IColonne[];

    fonctions: IFonction[];

    dimensions: IDimension[];

    typeextractions: ITypeExtraction[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected monitorService: MonitorService,
        protected colonneService: ColonneService,
        protected fonctionService: FonctionService,
        protected dimensionService: DimensionService,
        protected typeExtractionService: TypeExtractionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ monitor }) => {
            this.monitor = monitor;
        });
        this.colonneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IColonne[]>) => mayBeOk.ok),
                map((response: HttpResponse<IColonne[]>) => response.body)
            )
            .subscribe((res: IColonne[]) => (this.colonnes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.fonctionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFonction[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFonction[]>) => response.body)
            )
            .subscribe((res: IFonction[]) => (this.fonctions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.dimensionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDimension[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDimension[]>) => response.body)
            )
            .subscribe((res: IDimension[]) => (this.dimensions = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.monitor.id !== undefined) {
            this.subscribeToSaveResponse(this.monitorService.update(this.monitor));
        } else {
            this.subscribeToSaveResponse(this.monitorService.create(this.monitor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonitor>>) {
        result.subscribe((res: HttpResponse<IMonitor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackColonneById(index: number, item: IColonne) {
        return item.id;
    }

    trackFonctionById(index: number, item: IFonction) {
        return item.id;
    }

    trackDimensionById(index: number, item: IDimension) {
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
