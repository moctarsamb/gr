import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBase } from 'app/shared/model/base.model';
import { BaseService } from './base.service';

@Component({
    selector: 'jhi-base-delete-dialog',
    templateUrl: './base-delete-dialog.component.html'
})
export class BaseDeleteDialogComponent {
    base: IBase;

    constructor(protected baseService: BaseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.baseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'baseListModification',
                content: 'Deleted an base'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-base-delete-popup',
    template: ''
})
export class BaseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ base }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BaseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.base = base;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/base', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/base', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
