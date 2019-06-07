import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonitor } from 'app/shared/model/monitor.model';
import { MonitorService } from './monitor.service';

@Component({
    selector: 'jhi-monitor-delete-dialog',
    templateUrl: './monitor-delete-dialog.component.html'
})
export class MonitorDeleteDialogComponent {
    monitor: IMonitor;

    constructor(protected monitorService: MonitorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.monitorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'monitorListModification',
                content: 'Deleted an monitor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-monitor-delete-popup',
    template: ''
})
export class MonitorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monitor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MonitorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.monitor = monitor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/monitor', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/monitor', { outlets: { popup: null } }]);
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
