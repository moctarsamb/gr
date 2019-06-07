import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClause } from 'app/shared/model/clause.model';

type EntityResponseType = HttpResponse<IClause>;
type EntityArrayResponseType = HttpResponse<IClause[]>;

@Injectable({ providedIn: 'root' })
export class ClauseService {
    public resourceUrl = SERVER_API_URL + 'api/clauses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/clauses';

    constructor(protected http: HttpClient) {}

    create(clause: IClause): Observable<EntityResponseType> {
        return this.http.post<IClause>(this.resourceUrl, clause, { observe: 'response' });
    }

    update(clause: IClause): Observable<EntityResponseType> {
        return this.http.put<IClause>(this.resourceUrl, clause, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClause>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClause[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClause[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
