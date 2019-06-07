import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProvenance } from 'app/shared/model/provenance.model';
import { ProvenanceService } from './provenance.service';

@Component({
    selector: 'jhi-provenance-delete-dialog',
    templateUrl: './provenance-delete-dialog.component.html'
})
export class ProvenanceDeleteDialogComponent {
    provenance: IProvenance;

    constructor(
        protected provenanceService: ProvenanceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.provenanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'provenanceListModification',
                content: 'Deleted an provenance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-provenance-delete-popup',
    template: ''
})
export class ProvenanceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ provenance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProvenanceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.provenance = provenance;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/provenance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/provenance', { outlets: { popup: null } }]);
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
