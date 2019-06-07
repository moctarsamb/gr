import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFlux } from 'app/shared/model/flux.model';
import { FluxService } from './flux.service';

@Component({
    selector: 'jhi-flux-delete-dialog',
    templateUrl: './flux-delete-dialog.component.html'
})
export class FluxDeleteDialogComponent {
    flux: IFlux;

    constructor(protected fluxService: FluxService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fluxService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fluxListModification',
                content: 'Deleted an flux'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-flux-delete-popup',
    template: ''
})
export class FluxDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ flux }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FluxDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.flux = flux;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/flux', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/flux', { outlets: { popup: null } }]);
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
