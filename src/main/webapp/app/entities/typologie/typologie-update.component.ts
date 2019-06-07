import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypologie } from 'app/shared/model/typologie.model';
import { TypologieService } from './typologie.service';

@Component({
    selector: 'jhi-typologie-update',
    templateUrl: './typologie-update.component.html'
})
export class TypologieUpdateComponent implements OnInit {
    typologie: ITypologie;
    isSaving: boolean;

    constructor(protected typologieService: TypologieService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typologie }) => {
            this.typologie = typologie;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typologie.id !== undefined) {
            this.subscribeToSaveResponse(this.typologieService.update(this.typologie));
        } else {
            this.subscribeToSaveResponse(this.typologieService.create(this.typologie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypologie>>) {
        result.subscribe((res: HttpResponse<ITypologie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
