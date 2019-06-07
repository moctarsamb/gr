import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';
import { OperateurLogiqueService } from './operateur-logique.service';

@Component({
    selector: 'jhi-operateur-logique-delete-dialog',
    templateUrl: './operateur-logique-delete-dialog.component.html'
})
export class OperateurLogiqueDeleteDialogComponent {
    operateurLogique: IOperateurLogique;

    constructor(
        protected operateurLogiqueService: OperateurLogiqueService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operateurLogiqueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'operateurLogiqueListModification',
                content: 'Deleted an operateurLogique'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operateur-logique-delete-popup',
    template: ''
})
export class OperateurLogiqueDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operateurLogique }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OperateurLogiqueDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.operateurLogique = operateurLogique;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/operateur-logique', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/operateur-logique', { outlets: { popup: null } }]);
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
