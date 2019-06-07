import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from './utilisateur.service';
import { IUser, UserService } from 'app/core';
import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from 'app/entities/profil';
import { IDroitAcces } from 'app/shared/model/droit-acces.model';
import { DroitAccesService } from 'app/entities/droit-acces';
import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from 'app/entities/filiale';

@Component({
    selector: 'jhi-utilisateur-update',
    templateUrl: './utilisateur-update.component.html'
})
export class UtilisateurUpdateComponent implements OnInit {
    utilisateur: IUtilisateur;
    isSaving: boolean;

    users: IUser[];

    droitacces: IDroitAcces[];

    filiales: IFiliale[];

    profils: IProfil[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected utilisateurService: UtilisateurService,
        protected userService: UserService,
        protected profilService: ProfilService,
        protected droitAccesService: DroitAccesService,
        protected filialeService: FilialeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ utilisateur }) => {
            this.utilisateur = utilisateur;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.droitAccesService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDroitAcces[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDroitAcces[]>) => response.body)
            )
            .subscribe((res: IDroitAcces[]) => (this.droitacces = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.filialeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFiliale[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFiliale[]>) => response.body)
            )
            .subscribe((res: IFiliale[]) => (this.filiales = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.profilService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProfil[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProfil[]>) => response.body)
            )
            .subscribe((res: IProfil[]) => (this.profils = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.utilisateur.id !== undefined) {
            this.subscribeToSaveResponse(this.utilisateurService.update(this.utilisateur));
        } else {
            this.subscribeToSaveResponse(this.utilisateurService.create(this.utilisateur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtilisateur>>) {
        result.subscribe((res: HttpResponse<IUtilisateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackDroitAccesById(index: number, item: IDroitAcces) {
        return item.id;
    }

    trackFilialeById(index: number, item: IFiliale) {
        return item.id;
    }

    trackProfilById(index: number, item: IProfil) {
        return item.id;
    }
}
