import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClause } from 'app/shared/model/clause.model';
import { ClauseService } from './clause.service';

@Component({
    selector: 'jhi-clause-delete-dialog',
    templateUrl: './clause-delete-dialog.component.html'
})
export class ClauseDeleteDialogComponent {
    clause: IClause;

    constructor(protected clauseService: ClauseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clauseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clauseListModification',
                content: 'Deleted an clause'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clause-delete-popup',
    template: ''
})
export class ClauseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ clause }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClauseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.clause = clause;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/clause', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/clause', { outlets: { popup: null } }]);
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
