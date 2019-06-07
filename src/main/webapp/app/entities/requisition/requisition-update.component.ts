import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from './requisition.service';
import { IProvenance } from 'app/shared/model/provenance.model';
import { ProvenanceService } from 'app/entities/provenance';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from 'app/entities/structure';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur';

@Component({
    selector: 'jhi-requisition-update',
    templateUrl: './requisition-update.component.html'
})
export class RequisitionUpdateComponent implements OnInit {
    requisition: IRequisition;
    isSaving: boolean;

    provenances: IProvenance[];

    structures: IStructure[];

    utilisateurs: IUtilisateur[];
    dateSaisiePvDp: any;
    dateArriveeDemande: string;
    dateSaisieDemande: string;
    dateReponse: string;
    dateRenvoieResultat: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionService: RequisitionService,
        protected provenanceService: ProvenanceService,
        protected structureService: StructureService,
        protected utilisateurService: UtilisateurService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisition }) => {
            this.requisition = requisition;
            this.dateArriveeDemande =
                this.requisition.dateArriveeDemande != null ? this.requisition.dateArriveeDemande.format(DATE_TIME_FORMAT) : null;
            this.dateSaisieDemande =
                this.requisition.dateSaisieDemande != null ? this.requisition.dateSaisieDemande.format(DATE_TIME_FORMAT) : null;
            this.dateReponse = this.requisition.dateReponse != null ? this.requisition.dateReponse.format(DATE_TIME_FORMAT) : null;
            this.dateRenvoieResultat =
                this.requisition.dateRenvoieResultat != null ? this.requisition.dateRenvoieResultat.format(DATE_TIME_FORMAT) : null;
        });
        this.provenanceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProvenance[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProvenance[]>) => response.body)
            )
            .subscribe((res: IProvenance[]) => (this.provenances = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.structureService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStructure[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStructure[]>) => response.body)
            )
            .subscribe((res: IStructure[]) => (this.structures = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.requisition.dateArriveeDemande = this.dateArriveeDemande != null ? moment(this.dateArriveeDemande, DATE_TIME_FORMAT) : null;
        this.requisition.dateSaisieDemande = this.dateSaisieDemande != null ? moment(this.dateSaisieDemande, DATE_TIME_FORMAT) : null;
        this.requisition.dateReponse = this.dateReponse != null ? moment(this.dateReponse, DATE_TIME_FORMAT) : null;
        this.requisition.dateRenvoieResultat = this.dateRenvoieResultat != null ? moment(this.dateRenvoieResultat, DATE_TIME_FORMAT) : null;
        if (this.requisition.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionService.update(this.requisition));
        } else {
            this.subscribeToSaveResponse(this.requisitionService.create(this.requisition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisition>>) {
        result.subscribe((res: HttpResponse<IRequisition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProvenanceById(index: number, item: IProvenance) {
        return item.id;
    }

    trackStructureById(index: number, item: IStructure) {
        return item.id;
    }

    trackUtilisateurById(index: number, item: IUtilisateur) {
        return item.id;
    }
}
