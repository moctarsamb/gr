import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { ChampsRechercheService } from './champs-recherche.service';
import { IResultat } from 'app/shared/model/resultat.model';
import { ResultatService } from 'app/entities/resultat';
import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from 'app/entities/colonne';
import { IEnvironnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from 'app/entities/environnement';
import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from 'app/entities/filiale';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from 'app/entities/type-extraction';

@Component({
    selector: 'jhi-champs-recherche-update',
    templateUrl: './champs-recherche-update.component.html'
})
export class ChampsRechercheUpdateComponent implements OnInit {
    champsRecherche: IChampsRecherche;
    isSaving: boolean;

    resultats: IResultat[];

    colonnes: IColonne[];

    environnements: IEnvironnement[];

    filiales: IFiliale[];

    requisitions: IRequisition[];

    typeextractions: ITypeExtraction[];
    dateDebutExtractionDp: any;
    dateFinExtractionDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected champsRechercheService: ChampsRechercheService,
        protected resultatService: ResultatService,
        protected colonneService: ColonneService,
        protected environnementService: EnvironnementService,
        protected filialeService: FilialeService,
        protected requisitionService: RequisitionService,
        protected typeExtractionService: TypeExtractionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ champsRecherche }) => {
            this.champsRecherche = champsRecherche;
        });
        this.resultatService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IResultat[]>) => mayBeOk.ok),
                map((response: HttpResponse<IResultat[]>) => response.body)
            )
            .subscribe((res: IResultat[]) => (this.resultats = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.colonneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IColonne[]>) => mayBeOk.ok),
                map((response: HttpResponse<IColonne[]>) => response.body)
            )
            .subscribe((res: IColonne[]) => (this.colonnes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.environnementService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEnvironnement[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEnvironnement[]>) => response.body)
            )
            .subscribe((res: IEnvironnement[]) => (this.environnements = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.filialeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFiliale[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFiliale[]>) => response.body)
            )
            .subscribe((res: IFiliale[]) => (this.filiales = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeExtractionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeExtraction[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeExtraction[]>) => response.body)
            )
            .subscribe((res: ITypeExtraction[]) => (this.typeextractions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.champsRecherche.id !== undefined) {
            this.subscribeToSaveResponse(this.champsRechercheService.update(this.champsRecherche));
        } else {
            this.subscribeToSaveResponse(this.champsRechercheService.create(this.champsRecherche));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IChampsRecherche>>) {
        result.subscribe((res: HttpResponse<IChampsRecherche>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackColonneById(index: number, item: IColonne) {
        return item.id;
    }

    trackEnvironnementById(index: number, item: IEnvironnement) {
        return item.id;
    }

    trackFilialeById(index: number, item: IFiliale) {
        return item.id;
    }

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }

    trackTypeExtractionById(index: number, item: ITypeExtraction) {
        return item.id;
    }
}
