import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEnvironnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from './environnement.service';
import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from 'app/entities/filiale';

@Component({
    selector: 'jhi-environnement-update',
    templateUrl: './environnement-update.component.html'
})
export class EnvironnementUpdateComponent implements OnInit {
    environnement: IEnvironnement;
    isSaving: boolean;

    filiales: IFiliale[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected environnementService: EnvironnementService,
        protected filialeService: FilialeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ environnement }) => {
            this.environnement = environnement;
        });
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
        if (this.environnement.id !== undefined) {
            this.subscribeToSaveResponse(this.environnementService.update(this.environnement));
        } else {
            this.subscribeToSaveResponse(this.environnementService.create(this.environnement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnvironnement>>) {
        result.subscribe((res: HttpResponse<IEnvironnement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
