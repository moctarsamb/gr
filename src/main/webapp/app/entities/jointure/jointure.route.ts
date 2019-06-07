import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Jointure } from 'app/shared/model/jointure.model';
import { JointureService } from './jointure.service';
import { JointureComponent } from './jointure.component';
import { JointureDetailComponent } from './jointure-detail.component';
import { JointureUpdateComponent } from './jointure-update.component';
import { JointureDeletePopupComponent } from './jointure-delete-dialog.component';
import { IJointure } from 'app/shared/model/jointure.model';

@Injectable({ providedIn: 'root' })
export class JointureResolve implements Resolve<IJointure> {
    constructor(private service: JointureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJointure> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Jointure>) => response.ok),
                map((jointure: HttpResponse<Jointure>) => jointure.body)
            );
        }
        return of(new Jointure());
    }
}

export const jointureRoute: Routes = [
    {
        path: '',
        component: JointureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.jointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JointureDetailComponent,
        resolve: {
            jointure: JointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.jointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JointureUpdateComponent,
        resolve: {
            jointure: JointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.jointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JointureUpdateComponent,
        resolve: {
            jointure: JointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.jointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jointurePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JointureDeletePopupComponent,
        resolve: {
            jointure: JointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.jointure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
