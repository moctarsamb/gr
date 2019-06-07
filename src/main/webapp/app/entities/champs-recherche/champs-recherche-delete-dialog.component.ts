import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';
import { ChampsRechercheService } from './champs-recherche.service';

@Component({
    selector: 'jhi-champs-recherche-delete-dialog',
    templateUrl: './champs-recherche-delete-dialog.component.html'
})
export class ChampsRechercheDeleteDialogComponent {
    champsRecherche: IChampsRecherche;

    constructor(
        protected champsRechercheService: ChampsRechercheService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.champsRechercheService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'champsRechercheListModification',
                content: 'Deleted an champsRecherche'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-champs-recherche-delete-popup',
    template: ''
})
export class ChampsRechercheDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ champsRecherche }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChampsRechercheDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.champsRecherche = champsRecherche;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/champs-recherche', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/champs-recherche', { outlets: { popup: null } }]);
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
