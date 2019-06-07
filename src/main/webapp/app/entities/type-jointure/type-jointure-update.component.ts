import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeJointure } from 'app/shared/model/type-jointure.model';
import { TypeJointureService } from './type-jointure.service';

@Component({
    selector: 'jhi-type-jointure-update',
    templateUrl: './type-jointure-update.component.html'
})
export class TypeJointureUpdateComponent implements OnInit {
    typeJointure: ITypeJointure;
    isSaving: boolean;

    constructor(protected typeJointureService: TypeJointureService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeJointure }) => {
            this.typeJointure = typeJointure;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeJointure.id !== undefined) {
            this.subscribeToSaveResponse(this.typeJointureService.update(this.typeJointure));
        } else {
            this.subscribeToSaveResponse(this.typeJointureService.create(this.typeJointure));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeJointure>>) {
        result.subscribe((res: HttpResponse<ITypeJointure>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
