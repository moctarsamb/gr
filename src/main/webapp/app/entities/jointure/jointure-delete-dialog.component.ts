import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJointure } from 'app/shared/model/jointure.model';
import { JointureService } from './jointure.service';

@Component({
    selector: 'jhi-jointure-delete-dialog',
    templateUrl: './jointure-delete-dialog.component.html'
})
export class JointureDeleteDialogComponent {
    jointure: IJointure;

    constructor(protected jointureService: JointureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jointureService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'jointureListModification',
                content: 'Deleted an jointure'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-jointure-delete-popup',
    template: ''
})
export class JointureDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jointure }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JointureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.jointure = jointure;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/jointure', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/jointure', { outlets: { popup: null } }]);
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
