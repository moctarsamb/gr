import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDroitAcces } from 'app/shared/model/droit-acces.model';
import { DroitAccesService } from './droit-acces.service';

@Component({
    selector: 'jhi-droit-acces-delete-dialog',
    templateUrl: './droit-acces-delete-dialog.component.html'
})
export class DroitAccesDeleteDialogComponent {
    droitAcces: IDroitAcces;

    constructor(
        protected droitAccesService: DroitAccesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.droitAccesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'droitAccesListModification',
                content: 'Deleted an droitAcces'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-droit-acces-delete-popup',
    template: ''
})
export class DroitAccesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ droitAcces }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DroitAccesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.droitAcces = droitAcces;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/droit-acces', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/droit-acces', { outlets: { popup: null } }]);
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
