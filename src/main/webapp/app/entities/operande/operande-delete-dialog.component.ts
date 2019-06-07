import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperande } from 'app/shared/model/operande.model';
import { OperandeService } from './operande.service';

@Component({
    selector: 'jhi-operande-delete-dialog',
    templateUrl: './operande-delete-dialog.component.html'
})
export class OperandeDeleteDialogComponent {
    operande: IOperande;

    constructor(protected operandeService: OperandeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operandeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'operandeListModification',
                content: 'Deleted an operande'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operande-delete-popup',
    template: ''
})
export class OperandeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operande }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OperandeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.operande = operande;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/operande', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/operande', { outlets: { popup: null } }]);
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
