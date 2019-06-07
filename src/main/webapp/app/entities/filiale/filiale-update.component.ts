import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from './filiale.service';
import { IEnvironnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from 'app/entities/environnement';
import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from 'app/entities/profil';

@Component({
    selector: 'jhi-filiale-update',
    templateUrl: './filiale-update.component.html'
})
export class FilialeUpdateComponent implements OnInit {
    filiale: IFiliale;
    isSaving: boolean;

    environnements: IEnvironnement[];

    profils: IProfil[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected filialeService: FilialeService,
        protected environnementService: EnvironnementService,
        protected profilService: ProfilService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ filiale }) => {
            this.filiale = filiale;
        });
        this.environnementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEnvironnement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEnvironnement[]>) => response.body)
            )
            .subscribe((res: IEnvironnement[]) => (this.environnements = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.filiale.id !== undefined) {
            this.subscribeToSaveResponse(this.filialeService.update(this.filiale));
        } else {
            this.subscribeToSaveResponse(this.filialeService.create(this.filiale));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFiliale>>) {
        result.subscribe((res: HttpResponse<IFiliale>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEnvironnementById(index: number, item: IEnvironnement) {
        return item.id;
    }

    trackProfilById(index: number, item: IProfil) {
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
