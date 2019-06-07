import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypologie } from 'app/shared/model/typologie.model';
import { TypologieService } from './typologie.service';

@Component({
    selector: 'jhi-typologie-delete-dialog',
    templateUrl: './typologie-delete-dialog.component.html'
})
export class TypologieDeleteDialogComponent {
    typologie: ITypologie;

    constructor(
        protected typologieService: TypologieService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typologieService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typologieListModification',
                content: 'Deleted an typologie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-typologie-delete-popup',
    template: ''
})
export class TypologieDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typologie }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypologieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.typologie = typologie;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/typologie', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/typologie', { outlets: { popup: null } }]);
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
