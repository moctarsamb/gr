import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeJointure } from 'app/shared/model/type-jointure.model';

type EntityResponseType = HttpResponse<ITypeJointure>;
type EntityArrayResponseType = HttpResponse<ITypeJointure[]>;

@Injectable({ providedIn: 'root' })
export class TypeJointureService {
    public resourceUrl = SERVER_API_URL + 'api/type-jointures';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-jointures';

    constructor(protected http: HttpClient) {}

    create(typeJointure: ITypeJointure): Observable<EntityResponseType> {
        return this.http.post<ITypeJointure>(this.resourceUrl, typeJointure, { observe: 'response' });
    }

    update(typeJointure: ITypeJointure): Observable<EntityResponseType> {
        return this.http.put<ITypeJointure>(this.resourceUrl, typeJointure, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeJointure>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeJointure[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeJointure[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
