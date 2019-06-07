import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from './type-extraction.service';

@Component({
    selector: 'jhi-type-extraction-delete-dialog',
    templateUrl: './type-extraction-delete-dialog.component.html'
})
export class TypeExtractionDeleteDialogComponent {
    typeExtraction: ITypeExtraction;

    constructor(
        protected typeExtractionService: TypeExtractionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeExtractionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeExtractionListModification',
                content: 'Deleted an typeExtraction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-extraction-delete-popup',
    template: ''
})
export class TypeExtractionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeExtraction }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeExtractionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeExtraction = typeExtraction;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-extraction', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-extraction', { outlets: { popup: null } }]);
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
