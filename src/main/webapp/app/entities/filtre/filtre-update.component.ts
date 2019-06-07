import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFiltre } from 'app/shared/model/filtre.model';
import { FiltreService } from './filtre.service';
import { IClause } from 'app/shared/model/clause.model';
import { ClauseService } from 'app/entities/clause';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';
import { OperateurLogiqueService } from 'app/entities/operateur-logique';

@Component({
    selector: 'jhi-filtre-update',
    templateUrl: './filtre-update.component.html'
})
export class FiltreUpdateComponent implements OnInit {
    filtre: IFiltre;
    isSaving: boolean;

    clauses: IClause[];

    operateurlogiques: IOperateurLogique[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected filtreService: FiltreService,
        protected clauseService: ClauseService,
        protected operateurLogiqueService: OperateurLogiqueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ filtre }) => {
            this.filtre = filtre;
        });
        this.clauseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClause[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClause[]>) => response.body)
            )
            .subscribe((res: IClause[]) => (this.clauses = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.operateurLogiqueService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOperateurLogique[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOperateurLogique[]>) => response.body)
            )
            .subscribe((res: IOperateurLogique[]) => (this.operateurlogiques = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.filtre.id !== undefined) {
            this.subscribeToSaveResponse(this.filtreService.update(this.filtre));
        } else {
            this.subscribeToSaveResponse(this.filtreService.create(this.filtre));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFiltre>>) {
        result.subscribe((res: HttpResponse<IFiltre>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClauseById(index: number, item: IClause) {
        return item.id;
    }

    trackOperateurLogiqueById(index: number, item: IOperateurLogique) {
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
