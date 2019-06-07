import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOperande } from 'app/shared/model/operande.model';
import { OperandeService } from './operande.service';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from 'app/entities/colonne';
import { IValeur } from 'app/shared/model/valeur.model';
import { ValeurService } from 'app/entities/valeur';
import { IFonction } from 'app/shared/model/fonction.model';
import { FonctionService } from 'app/entities/fonction';
import { IClause } from 'app/shared/model/clause.model';
import { ClauseService } from 'app/entities/clause';

@Component({
    selector: 'jhi-operande-update',
    templateUrl: './operande-update.component.html'
})
export class OperandeUpdateComponent implements OnInit {
    operande: IOperande;
    isSaving: boolean;

    colonnes: IColonne[];

    valeurs: IValeur[];

    fonctions: IFonction[];

    clauses: IClause[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected operandeService: OperandeService,
        protected colonneService: ColonneService,
        protected valeurService: ValeurService,
        protected fonctionService: FonctionService,
        protected clauseService: ClauseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operande }) => {
            this.operande = operande;
        });
        this.colonneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IColonne[]>) => mayBeOk.ok),
                map((response: HttpResponse<IColonne[]>) => response.body)
            )
            .subscribe((res: IColonne[]) => (this.colonnes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.valeurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IValeur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IValeur[]>) => response.body)
            )
            .subscribe((res: IValeur[]) => (this.valeurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.fonctionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFonction[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFonction[]>) => response.body)
            )
            .subscribe((res: IFonction[]) => (this.fonctions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.clauseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClause[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClause[]>) => response.body)
            )
            .subscribe((res: IClause[]) => (this.clauses = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operande.id !== undefined) {
            this.subscribeToSaveResponse(this.operandeService.update(this.operande));
        } else {
            this.subscribeToSaveResponse(this.operandeService.create(this.operande));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperande>>) {
        result.subscribe((res: HttpResponse<IOperande>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackValeurById(index: number, item: IValeur) {
        return item.id;
    }

    trackFonctionById(index: number, item: IFonction) {
        return item.id;
    }

    trackClauseById(index: number, item: IClause) {
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
