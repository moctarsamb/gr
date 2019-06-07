import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeJointure } from 'app/shared/model/type-jointure.model';
import { TypeJointureService } from './type-jointure.service';

@Component({
    selector: 'jhi-type-jointure-delete-dialog',
    templateUrl: './type-jointure-delete-dialog.component.html'
})
export class TypeJointureDeleteDialogComponent {
    typeJointure: ITypeJointure;

    constructor(
        protected typeJointureService: TypeJointureService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeJointureService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeJointureListModification',
                content: 'Deleted an typeJointure'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-jointure-delete-popup',
    template: ''
})
export class TypeJointureDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeJointure }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeJointureDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeJointure = typeJointure;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-jointure', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-jointure', { outlets: { popup: null } }]);
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
