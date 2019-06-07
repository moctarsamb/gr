import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Structure } from 'app/shared/model/structure.model';
import { StructureService } from './structure.service';
import { StructureComponent } from './structure.component';
import { StructureDetailComponent } from './structure-detail.component';
import { StructureUpdateComponent } from './structure-update.component';
import { StructureDeletePopupComponent } from './structure-delete-dialog.component';
import { IStructure } from 'app/shared/model/structure.model';

@Injectable({ providedIn: 'root' })
export class StructureResolve implements Resolve<IStructure> {
    constructor(private service: StructureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStructure> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Structure>) => response.ok),
                map((structure: HttpResponse<Structure>) => structure.body)
            );
        }
        return of(new Structure());
    }
}

export const structureRoute: Routes = [
    {
        path: '',
        component: StructureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StructureDetailComponent,
        resolve: {
            structure: StructureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StructureUpdateComponent,
        resolve: {
            structure: StructureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StructureUpdateComponent,
        resolve: {
            structure: StructureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const structurePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StructureDeletePopupComponent,
        resolve: {
            structure: StructureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
