import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFiliale } from 'app/shared/model/filiale.model';
import { FilialeService } from './filiale.service';

@Component({
    selector: 'jhi-filiale-delete-dialog',
    templateUrl: './filiale-delete-dialog.component.html'
})
export class FilialeDeleteDialogComponent {
    filiale: IFiliale;

    constructor(protected filialeService: FilialeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filialeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'filialeListModification',
                content: 'Deleted an filiale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-filiale-delete-popup',
    template: ''
})
export class FilialeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ filiale }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FilialeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.filiale = filiale;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/filiale', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/filiale', { outlets: { popup: null } }]);
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
