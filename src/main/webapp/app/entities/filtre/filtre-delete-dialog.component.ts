import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFiltre } from 'app/shared/model/filtre.model';
import { FiltreService } from './filtre.service';

@Component({
    selector: 'jhi-filtre-delete-dialog',
    templateUrl: './filtre-delete-dialog.component.html'
})
export class FiltreDeleteDialogComponent {
    filtre: IFiltre;

    constructor(protected filtreService: FiltreService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filtreService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'filtreListModification',
                content: 'Deleted an filtre'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filtre-delete-popup',
    template: ''
})
export class FiltreDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filtre }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FiltreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.filtre = filtre;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/filtre', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/filtre', { outlets: { popup: null } }]);
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
