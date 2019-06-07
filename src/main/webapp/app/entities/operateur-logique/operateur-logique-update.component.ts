import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';
import { OperateurLogiqueService } from './operateur-logique.service';

@Component({
    selector: 'jhi-operateur-logique-update',
    templateUrl: './operateur-logique-update.component.html'
})
export class OperateurLogiqueUpdateComponent implements OnInit {
    operateurLogique: IOperateurLogique;
    isSaving: boolean;

    constructor(protected operateurLogiqueService: OperateurLogiqueService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operateurLogique }) => {
            this.operateurLogique = operateurLogique;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operateurLogique.id !== undefined) {
            this.subscribeToSaveResponse(this.operateurLogiqueService.update(this.operateurLogique));
        } else {
            this.subscribeToSaveResponse(this.operateurLogiqueService.create(this.operateurLogique));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperateurLogique>>) {
        result.subscribe((res: HttpResponse<IOperateurLogique>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
