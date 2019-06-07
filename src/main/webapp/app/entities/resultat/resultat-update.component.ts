import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResultat } from 'app/shared/model/resultat.model';
import { ResultatService } from './resultat.service';
import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { ChampsRechercheService } from 'app/entities/champs-recherche';

@Component({
    selector: 'jhi-resultat-update',
    templateUrl: './resultat-update.component.html'
})
export class ResultatUpdateComponent implements OnInit {
    resultat: IResultat;
    isSaving: boolean;

    champsrecherches: IChampsRecherche[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected resultatService: ResultatService,
        protected champsRechercheService: ChampsRechercheService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resultat }) => {
            this.resultat = resultat;
        });
        this.champsRechercheService
            .query({ 'resultatId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IChampsRecherche[]>) => mayBeOk.ok),
                map((response: HttpResponse<IChampsRecherche[]>) => response.body)
            )
            .subscribe(
                (res: IChampsRecherche[]) => {
                    if (!this.resultat.champsRecherche || !this.resultat.champsRecherche.id) {
                        this.champsrecherches = res;
                    } else {
                        this.champsRechercheService
                            .find(this.resultat.champsRecherche.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IChampsRecherche>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IChampsRecherche>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IChampsRecherche) => (this.champsrecherches = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resultat.id !== undefined) {
            this.subscribeToSaveResponse(this.resultatService.update(this.resultat));
        } else {
            this.subscribeToSaveResponse(this.resultatService.create(this.resultat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultat>>) {
        result.subscribe((res: HttpResponse<IResultat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackChampsRechercheById(index: number, item: IChampsRecherche) {
        return item.id;
    }
}
