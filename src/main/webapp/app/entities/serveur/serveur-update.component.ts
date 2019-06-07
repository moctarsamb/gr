import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IServeur } from 'app/shared/model/serveur.model';
import { ServeurService } from './serveur.service';

@Component({
    selector: 'jhi-serveur-update',
    templateUrl: './serveur-update.component.html'
})
export class ServeurUpdateComponent implements OnInit {
    serveur: IServeur;
    isSaving: boolean;

    constructor(protected serveurService: ServeurService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ serveur }) => {
            this.serveur = serveur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.serveur.id !== undefined) {
            this.subscribeToSaveResponse(this.serveurService.update(this.serveur));
        } else {
            this.subscribeToSaveResponse(this.serveurService.create(this.serveur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IServeur>>) {
        result.subscribe((res: HttpResponse<IServeur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
