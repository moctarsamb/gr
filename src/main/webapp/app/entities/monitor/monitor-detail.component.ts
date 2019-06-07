import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonitor } from 'app/shared/model/monitor.model';

@Component({
    selector: 'jhi-monitor-detail',
    templateUrl: './monitor-detail.component.html'
})
export class MonitorDetailComponent implements OnInit {
    monitor: IMonitor;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monitor }) => {
            this.monitor = monitor;
        });
    }

    previousState() {
        window.history.back();
    }
}
