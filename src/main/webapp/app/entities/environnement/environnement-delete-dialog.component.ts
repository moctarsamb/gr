import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnvironnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from './environnement.service';

@Component({
    selector: 'jhi-environnement-delete-dialog',
    templateUrl: './environnement-delete-dialog.component.html'
})
export class EnvironnementDeleteDialogComponent {
    environnement: IEnvironnement;

    constructor(
        protected environnementService: EnvironnementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.environnementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'environnementListModification',
                content: 'Deleted an environnement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-environnement-delete-popup',
    template: ''
})
export class EnvironnementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ environnement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnvironnementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.environnement = environnement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/environnement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/environnement', { outlets: { popup: null } }]);
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
