import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from './colonne.service';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from 'app/entities/type-extraction';
import { IFlux } from 'app/shared/model/flux.model';
import { FluxService } from 'app/entities/flux';
import { IOperande } from 'app/shared/model/operande.model';
import { OperandeService } from 'app/entities/operande';
import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from 'app/entities/profil';

@Component({
    selector: 'jhi-colonne-update',
    templateUrl: './colonne-update.component.html'
})
export class ColonneUpdateComponent implements OnInit {
    colonne: IColonne;
    isSaving: boolean;

    typeextractions: ITypeExtraction[];

    fluxes: IFlux[];

    operandes: IOperande[];

    profils: IProfil[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected colonneService: ColonneService,
        protected typeExtractionService: TypeExtractionService,
        protected fluxService: FluxService,
        protected operandeService: OperandeService,
        protected profilService: ProfilService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ colonne }) => {
            this.colonne = colonne;
        });
        this.typeExtractionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExtraction[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExtraction[]>) => response.body)
            )
            .subscribe((res: ITypeExtraction[]) => (this.typeextractions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.fluxService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFlux[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFlux[]>) => response.body)
            )
            .subscribe((res: IFlux[]) => (this.fluxes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.operandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOperande[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOperande[]>) => response.body)
            )
            .subscribe((res: IOperande[]) => (this.operandes = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.colonne.id !== undefined) {
            this.subscribeToSaveResponse(this.colonneService.update(this.colonne));
        } else {
            this.subscribeToSaveResponse(this.colonneService.create(this.colonne));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IColonne>>) {
        result.subscribe((res: HttpResponse<IColonne>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeExtractionById(index: number, item: ITypeExtraction) {
        return item.id;
    }

    trackFluxById(index: number, item: IFlux) {
        return item.id;
    }

    trackOperandeById(index: number, item: IOperande) {
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
