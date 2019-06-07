import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from './structure.service';
import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from 'app/entities/filiale';

@Component({
    selector: 'jhi-structure-update',
    templateUrl: './structure-update.component.html'
})
export class StructureUpdateComponent implements OnInit {
    structure: IStructure;
    isSaving: boolean;

    filiales: IFiliale[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected structureService: StructureService,
        protected filialeService: FilialeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ structure }) => {
            this.structure = structure;
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
        if (this.structure.id !== undefined) {
            this.subscribeToSaveResponse(this.structureService.update(this.structure));
        } else {
            this.subscribeToSaveResponse(this.structureService.create(this.structure));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStructure>>) {
        result.subscribe((res: HttpResponse<IStructure>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
