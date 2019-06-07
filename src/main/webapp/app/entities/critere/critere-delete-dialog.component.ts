import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICritere } from 'app/shared/model/critere.model';
import { CritereService } from './critere.service';

@Component({
    selector: 'jhi-critere-delete-dialog',
    templateUrl: './critere-delete-dialog.component.html'
})
export class CritereDeleteDialogComponent {
    critere: ICritere;

    constructor(protected critereService: CritereService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.critereService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'critereListModification',
                content: 'Deleted an critere'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-critere-delete-popup',
    template: ''
})
export class CritereDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ critere }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CritereDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.critere = critere;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/critere', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/critere', { outlets: { popup: null } }]);
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
