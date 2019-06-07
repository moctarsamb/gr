import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Provenance } from 'app/shared/model/provenance.model';
import { ProvenanceService } from './provenance.service';
import { ProvenanceComponent } from './provenance.component';
import { ProvenanceDetailComponent } from './provenance-detail.component';
import { ProvenanceUpdateComponent } from './provenance-update.component';
import { ProvenanceDeletePopupComponent } from './provenance-delete-dialog.component';
import { IProvenance } from 'app/shared/model/provenance.model';

@Injectable({ providedIn: 'root' })
export class ProvenanceResolve implements Resolve<IProvenance> {
    constructor(private service: ProvenanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProvenance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Provenance>) => response.ok),
                map((provenance: HttpResponse<Provenance>) => provenance.body)
            );
        }
        return of(new Provenance());
    }
}

export const provenanceRoute: Routes = [
    {
        path: '',
        component: ProvenanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.provenance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProvenanceDetailComponent,
        resolve: {
            provenance: ProvenanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.provenance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProvenanceUpdateComponent,
        resolve: {
            provenance: ProvenanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.provenance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProvenanceUpdateComponent,
        resolve: {
            provenance: ProvenanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.provenance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const provenancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProvenanceDeletePopupComponent,
        resolve: {
            provenance: ProvenanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.provenance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
