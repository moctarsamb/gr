import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IClause } from 'app/shared/model/clause.model';
import { ClauseService } from './clause.service';
import { IOperande } from 'app/shared/model/operande.model';
import { OperandeService } from 'app/entities/operande';
import { IOperateur } from 'app/shared/model/operateur.model';
import { OperateurService } from 'app/entities/operateur';
import { IFiltre } from 'app/shared/model/filtre.model';
import { FiltreService } from 'app/entities/filtre';

@Component({
    selector: 'jhi-clause-update',
    templateUrl: './clause-update.component.html'
})
export class ClauseUpdateComponent implements OnInit {
    clause: IClause;
    isSaving: boolean;

    operandes: IOperande[];

    operateurs: IOperateur[];

    filtres: IFiltre[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected clauseService: ClauseService,
        protected operandeService: OperandeService,
        protected operateurService: OperateurService,
        protected filtreService: FiltreService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ clause }) => {
            this.clause = clause;
        });
        this.operandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOperande[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOperande[]>) => response.body)
            )
            .subscribe((res: IOperande[]) => (this.operandes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.operateurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOperateur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOperateur[]>) => response.body)
            )
            .subscribe((res: IOperateur[]) => (this.operateurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.filtreService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFiltre[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFiltre[]>) => response.body)
            )
            .subscribe((res: IFiltre[]) => (this.filtres = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.clause.id !== undefined) {
            this.subscribeToSaveResponse(this.clauseService.update(this.clause));
        } else {
            this.subscribeToSaveResponse(this.clauseService.create(this.clause));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IClause>>) {
        result.subscribe((res: HttpResponse<IClause>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOperandeById(index: number, item: IOperande) {
        return item.id;
    }

    trackOperateurById(index: number, item: IOperateur) {
        return item.id;
    }

    trackFiltreById(index: number, item: IFiltre) {
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
