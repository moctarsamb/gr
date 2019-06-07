import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColonne } from 'app/shared/model/colonne.model';
import { ColonneService } from './colonne.service';

@Component({
    selector: 'jhi-colonne-delete-dialog',
    templateUrl: './colonne-delete-dialog.component.html'
})
export class ColonneDeleteDialogComponent {
    colonne: IColonne;

    constructor(protected colonneService: ColonneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.colonneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'colonneListModification',
                content: 'Deleted an colonne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-colonne-delete-popup',
    template: ''
})
export class ColonneDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ colonne }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ColonneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.colonne = colonne;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/colonne', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/colonne', { outlets: { popup: null } }]);
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
