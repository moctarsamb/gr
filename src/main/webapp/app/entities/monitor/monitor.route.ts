import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Monitor } from 'app/shared/model/monitor.model';
import { MonitorService } from './monitor.service';
import { MonitorComponent } from './monitor.component';
import { MonitorDetailComponent } from './monitor-detail.component';
import { MonitorUpdateComponent } from './monitor-update.component';
import { MonitorDeletePopupComponent } from './monitor-delete-dialog.component';
import { IMonitor } from 'app/shared/model/monitor.model';

@Injectable({ providedIn: 'root' })
export class MonitorResolve implements Resolve<IMonitor> {
    constructor(private service: MonitorService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMonitor> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Monitor>) => response.ok),
                map((monitor: HttpResponse<Monitor>) => monitor.body)
            );
        }
        return of(new Monitor());
    }
}

export const monitorRoute: Routes = [
    {
        path: '',
        component: MonitorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.monitor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MonitorDetailComponent,
        resolve: {
            monitor: MonitorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.monitor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MonitorUpdateComponent,
        resolve: {
            monitor: MonitorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.monitor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MonitorUpdateComponent,
        resolve: {
            monitor: MonitorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.monitor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const monitorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MonitorDeletePopupComponent,
        resolve: {
            monitor: MonitorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.monitor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
