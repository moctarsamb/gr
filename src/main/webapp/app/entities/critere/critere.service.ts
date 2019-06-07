import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICritere } from 'app/shared/model/critere.model';

type EntityResponseType = HttpResponse<ICritere>;
type EntityArrayResponseType = HttpResponse<ICritere[]>;

@Injectable({ providedIn: 'root' })
export class CritereService {
    public resourceUrl = SERVER_API_URL + 'api/criteres';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/criteres';

    constructor(protected http: HttpClient) {}

    create(critere: ICritere): Observable<EntityResponseType> {
        return this.http.post<ICritere>(this.resourceUrl, critere, { observe: 'response' });
    }

    update(critere: ICritere): Observable<EntityResponseType> {
        return this.http.put<ICritere>(this.resourceUrl, critere, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICritere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICritere[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICritere[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
