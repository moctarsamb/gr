import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeJointure } from 'app/shared/model/type-jointure.model';
import { TypeJointureService } from './type-jointure.service';
import { TypeJointureComponent } from './type-jointure.component';
import { TypeJointureDetailComponent } from './type-jointure-detail.component';
import { TypeJointureUpdateComponent } from './type-jointure-update.component';
import { TypeJointureDeletePopupComponent } from './type-jointure-delete-dialog.component';
import { ITypeJointure } from 'app/shared/model/type-jointure.model';

@Injectable({ providedIn: 'root' })
export class TypeJointureResolve implements Resolve<ITypeJointure> {
    constructor(private service: TypeJointureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeJointure> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeJointure>) => response.ok),
                map((typeJointure: HttpResponse<TypeJointure>) => typeJointure.body)
            );
        }
        return of(new TypeJointure());
    }
}

export const typeJointureRoute: Routes = [
    {
        path: '',
        component: TypeJointureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeJointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeJointureDetailComponent,
        resolve: {
            typeJointure: TypeJointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeJointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeJointureUpdateComponent,
        resolve: {
            typeJointure: TypeJointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeJointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeJointureUpdateComponent,
        resolve: {
            typeJointure: TypeJointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeJointure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeJointurePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeJointureDeletePopupComponent,
        resolve: {
            typeJointure: TypeJointureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeJointure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
