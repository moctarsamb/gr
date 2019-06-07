import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Operande } from 'app/shared/model/operande.model';
import { OperandeService } from './operande.service';
import { OperandeComponent } from './operande.component';
import { OperandeDetailComponent } from './operande-detail.component';
import { OperandeUpdateComponent } from './operande-update.component';
import { OperandeDeletePopupComponent } from './operande-delete-dialog.component';
import { IOperande } from 'app/shared/model/operande.model';

@Injectable({ providedIn: 'root' })
export class OperandeResolve implements Resolve<IOperande> {
    constructor(private service: OperandeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOperande> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Operande>) => response.ok),
                map((operande: HttpResponse<Operande>) => operande.body)
            );
        }
        return of(new Operande());
    }
}

export const operandeRoute: Routes = [
    {
        path: '',
        component: OperandeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operande.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OperandeDetailComponent,
        resolve: {
            operande: OperandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operande.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OperandeUpdateComponent,
        resolve: {
            operande: OperandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operande.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OperandeUpdateComponent,
        resolve: {
            operande: OperandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operande.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operandePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OperandeDeletePopupComponent,
        resolve: {
            operande: OperandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.operande.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
