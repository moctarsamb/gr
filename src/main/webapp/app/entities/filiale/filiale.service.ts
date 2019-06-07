import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFiliale } from 'app/shared/model/filiale.model';

type EntityResponseType = HttpResponse<IFiliale>;
type EntityArrayResponseType = HttpResponse<IFiliale[]>;

@Injectable({ providedIn: 'root' })
export class FilialeService {
    public resourceUrl = SERVER_API_URL + 'api/filiales';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/filiales';

    constructor(protected http: HttpClient) {}

    create(filiale: IFiliale): Observable<EntityResponseType> {
        return this.http.post<IFiliale>(this.resourceUrl, filiale, { observe: 'response' });
    }

    update(filiale: IFiliale): Observable<EntityResponseType> {
        return this.http.put<IFiliale>(this.resourceUrl, filiale, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFiliale>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFiliale[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFiliale[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
