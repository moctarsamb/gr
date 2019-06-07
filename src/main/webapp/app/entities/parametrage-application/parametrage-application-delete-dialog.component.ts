import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParametrageApplication } from 'app/shared/model/parametrage-application.model';
import { ParametrageApplicationService } from './parametrage-application.service';

@Component({
    selector: 'jhi-parametrage-application-delete-dialog',
    templateUrl: './parametrage-application-delete-dialog.component.html'
})
export class ParametrageApplicationDeleteDialogComponent {
    parametrageApplication: IParametrageApplication;

    constructor(
        protected parametrageApplicationService: ParametrageApplicationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametrageApplicationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'parametrageApplicationListModification',
                content: 'Deleted an parametrageApplication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametrage-application-delete-popup',
    template: ''
})
export class ParametrageApplicationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parametrageApplication }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParametrageApplicationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.parametrageApplication = parametrageApplication;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/parametrage-application', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/parametrage-application', { outlets: { popup: null } }]);
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
