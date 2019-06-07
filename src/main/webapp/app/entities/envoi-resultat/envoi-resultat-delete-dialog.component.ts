import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';
import { EnvoiResultatService } from './envoi-resultat.service';

@Component({
    selector: 'jhi-envoi-resultat-delete-dialog',
    templateUrl: './envoi-resultat-delete-dialog.component.html'
})
export class EnvoiResultatDeleteDialogComponent {
    envoiResultat: IEnvoiResultat;

    constructor(
        protected envoiResultatService: EnvoiResultatService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.envoiResultatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'envoiResultatListModification',
                content: 'Deleted an envoiResultat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-envoi-resultat-delete-popup',
    template: ''
})
export class EnvoiResultatDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ envoiResultat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnvoiResultatDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.envoiResultat = envoiResultat;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/envoi-resultat', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/envoi-resultat', { outlets: { popup: null } }]);
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
