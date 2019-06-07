import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';
import { FichierResultatService } from './fichier-resultat.service';

@Component({
    selector: 'jhi-fichier-resultat-delete-dialog',
    templateUrl: './fichier-resultat-delete-dialog.component.html'
})
export class FichierResultatDeleteDialogComponent {
    fichierResultat: IFichierResultat;

    constructor(
        protected fichierResultatService: FichierResultatService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fichierResultatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fichierResultatListModification',
                content: 'Deleted an fichierResultat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fichier-resultat-delete-popup',
    template: ''
})
export class FichierResultatDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fichierResultat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FichierResultatDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.fichierResultat = fichierResultat;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fichier-resultat', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fichier-resultat', { outlets: { popup: null } }]);
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
