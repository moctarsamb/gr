import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from './profil.service';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from 'app/entities/colonne';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from 'app/entities/type-extraction';
import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from 'app/entities/filiale';

@Component({
    selector: 'jhi-profil-update',
    templateUrl: './profil-update.component.html'
})
export class ProfilUpdateComponent implements OnInit {
    profil: IProfil;
    isSaving: boolean;

    colonnes: IColonne[];

    typeextractions: ITypeExtraction[];

    utilisateurs: IUtilisateur[];

    filiales: IFiliale[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected profilService: ProfilService,
        protected utilisateurService: UtilisateurService,
        protected colonneService: ColonneService,
        protected typeExtractionService: TypeExtractionService,
        protected filialeService: FilialeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ profil }) => {
            this.profil = profil;
        });
        this.colonneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IColonne[]>) => mayBeOk.ok),
                map((response: HttpResponse<IColonne[]>) => response.body)
            )
            .subscribe((res: IColonne[]) => (this.colonnes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeExtractionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExtraction[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExtraction[]>) => response.body)
            )
            .subscribe((res: ITypeExtraction[]) => (this.typeextractions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.utilisateurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUtilisateur[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUtilisateur[]>) => response.body)
            )
            .subscribe((res: IUtilisateur[]) => (this.utilisateurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.filialeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFiliale[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFiliale[]>) => response.body)
            )
            .subscribe((res: IFiliale[]) => (this.filiales = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.profil.id !== undefined) {
            this.subscribeToSaveResponse(this.profilService.update(this.profil));
        } else {
            this.subscribeToSaveResponse(this.profilService.create(this.profil));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfil>>) {
        result.subscribe((res: HttpResponse<IProfil>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeExtractionById(index: number, item: ITypeExtraction) {
        return item.id;
    }

    trackUtilisateurById(index: number, item: IUtilisateur) {
        return item.id;
    }

    trackFilialeById(index: number, item: IFiliale) {
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
