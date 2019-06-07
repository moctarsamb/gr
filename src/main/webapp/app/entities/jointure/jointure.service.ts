import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJointure } from 'app/shared/model/jointure.model';

type EntityResponseType = HttpResponse<IJointure>;
type EntityArrayResponseType = HttpResponse<IJointure[]>;

@Injectable({ providedIn: 'root' })
export class JointureService {
    public resourceUrl = SERVER_API_URL + 'api/jointures';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/jointures';

    constructor(protected http: HttpClient) {}

    create(jointure: IJointure): Observable<EntityResponseType> {
        return this.http.post<IJointure>(this.resourceUrl, jointure, { observe: 'response' });
    }

    update(jointure: IJointure): Observable<EntityResponseType> {
        return this.http.put<IJointure>(this.resourceUrl, jointure, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IJointure>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJointure[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJointure[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
