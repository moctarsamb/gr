import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultat } from 'app/shared/model/resultat.model';
import { ResultatService } from './resultat.service';

@Component({
    selector: 'jhi-resultat-delete-dialog',
    templateUrl: './resultat-delete-dialog.component.html'
})
export class ResultatDeleteDialogComponent {
    resultat: IResultat;

    constructor(protected resultatService: ResultatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resultatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resultatListModification',
                content: 'Deleted an resultat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resultat-delete-popup',
    template: ''
})
export class ResultatDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResultatDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.resultat = resultat;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/resultat', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/resultat', { outlets: { popup: null } }]);
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
