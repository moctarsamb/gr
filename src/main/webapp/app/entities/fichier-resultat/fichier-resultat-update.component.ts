import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';
import { FichierResultatService } from './fichier-resultat.service';
import { IResultat } from 'app/shared/model/resultat.model';
import { ResultatService } from 'app/entities/resultat';

@Component({
    selector: 'jhi-fichier-resultat-update',
    templateUrl: './fichier-resultat-update.component.html'
})
export class FichierResultatUpdateComponent implements OnInit {
    fichierResultat: IFichierResultat;
    isSaving: boolean;

    resultats: IResultat[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected fichierResultatService: FichierResultatService,
        protected resultatService: ResultatService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fichierResultat }) => {
            this.fichierResultat = fichierResultat;
        });
        this.resultatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IResultat[]>) => mayBeOk.ok),
                map((response: HttpResponse<IResultat[]>) => response.body)
            )
            .subscribe((res: IResultat[]) => (this.resultats = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fichierResultat.id !== undefined) {
            this.subscribeToSaveResponse(this.fichierResultatService.update(this.fichierResultat));
        } else {
            this.subscribeToSaveResponse(this.fichierResultatService.create(this.fichierResultat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichierResultat>>) {
        result.subscribe((res: HttpResponse<IFichierResultat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackResultatById(index: number, item: IResultat) {
        return item.id;
    }
}
