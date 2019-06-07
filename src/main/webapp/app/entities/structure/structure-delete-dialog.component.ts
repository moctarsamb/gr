import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStructure } from 'app/shared/model/structure.model';
import { StructureService } from './structure.service';

@Component({
    selector: 'jhi-structure-delete-dialog',
    templateUrl: './structure-delete-dialog.component.html'
})
export class StructureDeleteDialogComponent {
    structure: IStructure;

    constructor(
        protected structureService: StructureService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.structureService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'structureListModification',
                content: 'Deleted an structure'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-structure-delete-popup',
    template: ''
})
export class StructureDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ structure }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StructureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.structure = structure;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/structure', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/structure', { outlets: { popup: null } }]);
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
