import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Filiale } from 'app/shared/model/filiale.model';
import { FilialeService } from './filiale.service';
import { FilialeComponent } from './filiale.component';
import { FilialeDetailComponent } from './filiale-detail.component';
import { FilialeUpdateComponent } from './filiale-update.component';
import { FilialeDeletePopupComponent } from './filiale-delete-dialog.component';
import { IFiliale } from 'app/shared/model/filiale.model';

@Injectable({ providedIn: 'root' })
export class FilialeResolve implements Resolve<IFiliale> {
    constructor(private service: FilialeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFiliale> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Filiale>) => response.ok),
                map((filiale: HttpResponse<Filiale>) => filiale.body)
            );
        }
        return of(new Filiale());
    }
}

export const filialeRoute: Routes = [
    {
        path: '',
        component: FilialeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filiale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FilialeDetailComponent,
        resolve: {
            filiale: FilialeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filiale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FilialeUpdateComponent,
        resolve: {
            filiale: FilialeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filiale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FilialeUpdateComponent,
        resolve: {
            filiale: FilialeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filiale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const filialePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FilialeDeletePopupComponent,
        resolve: {
            filiale: FilialeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.filiale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
