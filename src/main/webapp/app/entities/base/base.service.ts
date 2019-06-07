import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBase } from 'app/shared/model/base.model';

type EntityResponseType = HttpResponse<IBase>;
type EntityArrayResponseType = HttpResponse<IBase[]>;

@Injectable({ providedIn: 'root' })
export class BaseService {
    public resourceUrl = SERVER_API_URL + 'api/bases';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/bases';

    constructor(protected http: HttpClient) {}

    create(base: IBase): Observable<EntityResponseType> {
        return this.http.post<IBase>(this.resourceUrl, base, { observe: 'response' });
    }

    update(base: IBase): Observable<EntityResponseType> {
        return this.http.put<IBase>(this.resourceUrl, base, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBase[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
