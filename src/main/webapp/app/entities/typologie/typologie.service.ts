import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypologie } from 'app/shared/model/typologie.model';

type EntityResponseType = HttpResponse<ITypologie>;
type EntityArrayResponseType = HttpResponse<ITypologie[]>;

@Injectable({ providedIn: 'root' })
export class TypologieService {
    public resourceUrl = SERVER_API_URL + 'api/typologies';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/typologies';

    constructor(protected http: HttpClient) {}

    create(typologie: ITypologie): Observable<EntityResponseType> {
        return this.http.post<ITypologie>(this.resourceUrl, typologie, { observe: 'response' });
    }

    update(typologie: ITypologie): Observable<EntityResponseType> {
        return this.http.put<ITypologie>(this.resourceUrl, typologie, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypologie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypologie[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypologie[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
