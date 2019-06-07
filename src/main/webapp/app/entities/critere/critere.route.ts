import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Critere } from 'app/shared/model/critere.model';
import { CritereService } from './critere.service';
import { CritereComponent } from './critere.component';
import { CritereDetailComponent } from './critere-detail.component';
import { CritereUpdateComponent } from './critere-update.component';
import { CritereDeletePopupComponent } from './critere-delete-dialog.component';
import { ICritere } from 'app/shared/model/critere.model';

@Injectable({ providedIn: 'root' })
export class CritereResolve implements Resolve<ICritere> {
    constructor(private service: CritereService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICritere> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Critere>) => response.ok),
                map((critere: HttpResponse<Critere>) => critere.body)
            );
        }
        return of(new Critere());
    }
}

export const critereRoute: Routes = [
    {
        path: '',
        component: CritereComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.critere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CritereDetailComponent,
        resolve: {
            critere: CritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.critere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CritereUpdateComponent,
        resolve: {
            critere: CritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.critere.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CritereUpdateComponent,
        resolve: {
            critere: CritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.critere.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const criterePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CritereDeletePopupComponent,
        resolve: {
            critere: CritereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.critere.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
