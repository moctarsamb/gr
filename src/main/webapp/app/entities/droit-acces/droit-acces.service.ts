import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDroitAcces } from 'app/shared/model/droit-acces.model';

type EntityResponseType = HttpResponse<IDroitAcces>;
type EntityArrayResponseType = HttpResponse<IDroitAcces[]>;

@Injectable({ providedIn: 'root' })
export class DroitAccesService {
    public resourceUrl = SERVER_API_URL + 'api/droit-acces';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/droit-acces';

    constructor(protected http: HttpClient) {}

    create(droitAcces: IDroitAcces): Observable<EntityResponseType> {
        return this.http.post<IDroitAcces>(this.resourceUrl, droitAcces, { observe: 'response' });
    }

    update(droitAcces: IDroitAcces): Observable<EntityResponseType> {
        return this.http.put<IDroitAcces>(this.resourceUrl, droitAcces, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDroitAcces>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDroitAcces[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDroitAcces[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
