import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICritere } from 'app/shared/model/critere.model';
import { CritereService } from './critere.service';
import { IClause } from 'app/shared/model/clause.model';
import { ClauseService } from 'app/entities/clause';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';
import { OperateurLogiqueService } from 'app/entities/operateur-logique';

@Component({
    selector: 'jhi-critere-update',
    templateUrl: './critere-update.component.html'
})
export class CritereUpdateComponent implements OnInit {
    critere: ICritere;
    isSaving: boolean;

    clauses: IClause[];

    operateurlogiques: IOperateurLogique[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected critereService: CritereService,
        protected clauseService: ClauseService,
        protected operateurLogiqueService: OperateurLogiqueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ critere }) => {
            this.critere = critere;
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
        if (this.critere.id !== undefined) {
            this.subscribeToSaveResponse(this.critereService.update(this.critere));
        } else {
            this.subscribeToSaveResponse(this.critereService.create(this.critere));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICritere>>) {
        result.subscribe((res: HttpResponse<ICritere>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
