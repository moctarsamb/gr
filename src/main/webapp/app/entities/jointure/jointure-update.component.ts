import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IJointure } from 'app/shared/model/jointure.model';
import { JointureService } from './jointure.service';
import { ICritere } from 'app/shared/model/critere.model';
import { CritereService } from 'app/entities/critere';
import { IFlux } from 'app/shared/model/flux.model';
import { FluxService } from 'app/entities/flux';
import { ITypeJointure } from 'app/shared/model/type-jointure.model';
import { TypeJointureService } from 'app/entities/type-jointure';

@Component({
    selector: 'jhi-jointure-update',
    templateUrl: './jointure-update.component.html'
})
export class JointureUpdateComponent implements OnInit {
    jointure: IJointure;
    isSaving: boolean;

    criteres: ICritere[];

    fluxes: IFlux[];

    typejointures: ITypeJointure[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected jointureService: JointureService,
        protected critereService: CritereService,
        protected fluxService: FluxService,
        protected typeJointureService: TypeJointureService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jointure }) => {
            this.jointure = jointure;
        });
        this.critereService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICritere[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICritere[]>) => response.body)
            )
            .subscribe((res: ICritere[]) => (this.criteres = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.fluxService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFlux[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFlux[]>) => response.body)
            )
            .subscribe((res: IFlux[]) => (this.fluxes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeJointureService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeJointure[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeJointure[]>) => response.body)
            )
            .subscribe((res: ITypeJointure[]) => (this.typejointures = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.jointure.id !== undefined) {
            this.subscribeToSaveResponse(this.jointureService.update(this.jointure));
        } else {
            this.subscribeToSaveResponse(this.jointureService.create(this.jointure));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJointure>>) {
        result.subscribe((res: HttpResponse<IJointure>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCritereById(index: number, item: ICritere) {
        return item.id;
    }

    trackFluxById(index: number, item: IFlux) {
        return item.id;
    }

    trackTypeJointureById(index: number, item: ITypeJointure) {
        return item.id;
    }
}
