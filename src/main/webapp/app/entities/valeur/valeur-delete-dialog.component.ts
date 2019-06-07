import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IValeur } from 'app/shared/model/valeur.model';
import { ValeurService } from './valeur.service';

@Component({
    selector: 'jhi-valeur-delete-dialog',
    templateUrl: './valeur-delete-dialog.component.html'
})
export class ValeurDeleteDialogComponent {
    valeur: IValeur;

    constructor(protected valeurService: ValeurService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.valeurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'valeurListModification',
                content: 'Deleted an valeur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-valeur-delete-popup',
    template: ''
})
export class ValeurDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ valeur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ValeurDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.valeur = valeur;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/valeur', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/valeur', { outlets: { popup: null } }]);
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
