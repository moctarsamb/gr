import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperateur } from 'app/shared/model/operateur.model';
import { OperateurService } from './operateur.service';

@Component({
    selector: 'jhi-operateur-delete-dialog',
    templateUrl: './operateur-delete-dialog.component.html'
})
export class OperateurDeleteDialogComponent {
    operateur: IOperateur;

    constructor(
        protected operateurService: OperateurService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operateurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'operateurListModification',
                content: 'Deleted an operateur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-operateur-delete-popup',
    template: ''
})
export class OperateurDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ operateur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OperateurDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.operateur = operateur;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/operateur', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/operateur', { outlets: { popup: null } }]);
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
