import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IOperateur } from 'app/shared/model/operateur.model';
import { OperateurService } from './operateur.service';

@Component({
    selector: 'jhi-operateur-update',
    templateUrl: './operateur-update.component.html'
})
export class OperateurUpdateComponent implements OnInit {
    operateur: IOperateur;
    isSaving: boolean;

    constructor(protected operateurService: OperateurService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operateur }) => {
            this.operateur = operateur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operateur.id !== undefined) {
            this.subscribeToSaveResponse(this.operateurService.update(this.operateur));
        } else {
            this.subscribeToSaveResponse(this.operateurService.create(this.operateur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperateur>>) {
        result.subscribe((res: HttpResponse<IOperateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
