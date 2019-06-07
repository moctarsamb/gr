import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';
import { EnvoiResultatService } from './envoi-resultat.service';
import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';
import { FichierResultatService } from 'app/entities/fichier-resultat';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur';

@Component({
    selector: 'jhi-envoi-resultat-update',
    templateUrl: './envoi-resultat-update.component.html'
})
export class EnvoiResultatUpdateComponent implements OnInit {
    envoiResultat: IEnvoiResultat;
    isSaving: boolean;

    fichierresultats: IFichierResultat[];

    utilisateurs: IUtilisateur[];
    dateEnvoi: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected envoiResultatService: EnvoiResultatService,
        protected fichierResultatService: FichierResultatService,
        protected utilisateurService: UtilisateurService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ envoiResultat }) => {
            this.envoiResultat = envoiResultat;
            this.dateEnvoi = this.envoiResultat.dateEnvoi != null ? this.envoiResultat.dateEnvoi.format(DATE_TIME_FORMAT) : null;
        });
        this.fichierResultatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFichierResultat[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFichierResultat[]>) => response.body)
            )
            .subscribe((res: IFichierResultat[]) => (this.fichierresultats = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.utilisateurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUtilisateur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUtilisateur[]>) => response.body)
            )
            .subscribe((res: IUtilisateur[]) => (this.utilisateurs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.envoiResultat.dateEnvoi = this.dateEnvoi != null ? moment(this.dateEnvoi, DATE_TIME_FORMAT) : null;
        if (this.envoiResultat.id !== undefined) {
            this.subscribeToSaveResponse(this.envoiResultatService.update(this.envoiResultat));
        } else {
            this.subscribeToSaveResponse(this.envoiResultatService.create(this.envoiResultat));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnvoiResultat>>) {
        result.subscribe((res: HttpResponse<IEnvoiResultat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFichierResultatById(index: number, item: IFichierResultat) {
        return item.id;
    }

    trackUtilisateurById(index: number, item: IUtilisateur) {
        return item.id;
    }
}
