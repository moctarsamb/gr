import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from './type-extraction.service';
import { IMonitor } from 'app/shared/model/monitor.model';
import { MonitorService } from 'app/entities/monitor';
import { IBase } from 'app/shared/model/base.model';
import { BaseService } from 'app/entities/base';
import { IFiltre } from 'app/shared/model/filtre.model';
import { FiltreService } from 'app/entities/filtre';
import { IFlux } from 'app/shared/model/flux.model';
import { FluxService } from 'app/entities/flux';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from 'app/entities/colonne';
import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from 'app/entities/profil';

@Component({
    selector: 'jhi-type-extraction-update',
    templateUrl: './type-extraction-update.component.html'
})
export class TypeExtractionUpdateComponent implements OnInit {
    typeExtraction: ITypeExtraction;
    isSaving: boolean;

    monitors: IMonitor[];

    bases: IBase[];

    filtres: IFiltre[];

    fluxes: IFlux[];

    colonnes: IColonne[];

    profils: IProfil[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected typeExtractionService: TypeExtractionService,
        protected monitorService: MonitorService,
        protected baseService: BaseService,
        protected filtreService: FiltreService,
        protected fluxService: FluxService,
        protected colonneService: ColonneService,
        protected profilService: ProfilService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeExtraction }) => {
            this.typeExtraction = typeExtraction;
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
        this.filtreService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFiltre[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFiltre[]>) => response.body)
            )
            .subscribe((res: IFiltre[]) => (this.filtres = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.fluxService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFlux[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFlux[]>) => response.body)
            )
            .subscribe((res: IFlux[]) => (this.fluxes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.colonneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IColonne[]>) => mayBeOk.ok),
                map((response: HttpResponse<IColonne[]>) => response.body)
            )
            .subscribe((res: IColonne[]) => (this.colonnes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.profilService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProfil[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProfil[]>) => response.body)
            )
            .subscribe((res: IProfil[]) => (this.profils = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeExtraction.id !== undefined) {
            this.subscribeToSaveResponse(this.typeExtractionService.update(this.typeExtraction));
        } else {
            this.subscribeToSaveResponse(this.typeExtractionService.create(this.typeExtraction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeExtraction>>) {
        result.subscribe((res: HttpResponse<ITypeExtraction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFiltreById(index: number, item: IFiltre) {
        return item.id;
    }

    trackFluxById(index: number, item: IFlux) {
        return item.id;
    }

    trackColonneById(index: number, item: IColonne) {
        return item.id;
    }

    trackProfilById(index: number, item: IProfil) {
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
