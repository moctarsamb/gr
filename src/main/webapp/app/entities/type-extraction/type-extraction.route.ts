import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeExtraction } from 'app/shared/model/type-extraction.model';
import { TypeExtractionService } from './type-extraction.service';
import { TypeExtractionComponent } from './type-extraction.component';
import { TypeExtractionDetailComponent } from './type-extraction-detail.component';
import { TypeExtractionUpdateComponent } from './type-extraction-update.component';
import { TypeExtractionDeletePopupComponent } from './type-extraction-delete-dialog.component';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

@Injectable({ providedIn: 'root' })
export class TypeExtractionResolve implements Resolve<ITypeExtraction> {
    constructor(private service: TypeExtractionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeExtraction> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeExtraction>) => response.ok),
                map((typeExtraction: HttpResponse<TypeExtraction>) => typeExtraction.body)
            );
        }
        return of(new TypeExtraction());
    }
}

export const typeExtractionRoute: Routes = [
    {
        path: '',
        component: TypeExtractionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeExtraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeExtractionDetailComponent,
        resolve: {
            typeExtraction: TypeExtractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeExtraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeExtractionUpdateComponent,
        resolve: {
            typeExtraction: TypeExtractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeExtraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeExtractionUpdateComponent,
        resolve: {
            typeExtraction: TypeExtractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeExtraction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeExtractionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeExtractionDeletePopupComponent,
        resolve: {
            typeExtraction: TypeExtractionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'gestionrequisitionsfrontendgatewayApp.typeExtraction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
