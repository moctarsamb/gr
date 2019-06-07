import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IValeur } from 'app/shared/model/valeur.model';
import { ValeurService } from './valeur.service';
import { IOperande } from 'app/shared/model/operande.model';
import { OperandeService } from 'app/entities/operande';

@Component({
    selector: 'jhi-valeur-update',
    templateUrl: './valeur-update.component.html'
})
export class ValeurUpdateComponent implements OnInit {
    valeur: IValeur;
    isSaving: boolean;

    operandes: IOperande[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected valeurService: ValeurService,
        protected operandeService: OperandeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ valeur }) => {
            this.valeur = valeur;
        });
        this.operandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOperande[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOperande[]>) => response.body)
            )
            .subscribe((res: IOperande[]) => (this.operandes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.valeur.id !== undefined) {
            this.subscribeToSaveResponse(this.valeurService.update(this.valeur));
        } else {
            this.subscribeToSaveResponse(this.valeurService.create(this.valeur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IValeur>>) {
        result.subscribe((res: HttpResponse<IValeur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
