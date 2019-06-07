import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFiltre } from 'app/shared/model/filtre.model';

type EntityResponseType = HttpResponse<IFiltre>;
type EntityArrayResponseType = HttpResponse<IFiltre[]>;

@Injectable({ providedIn: 'root' })
export class FiltreService {
    public resourceUrl = SERVER_API_URL + 'api/filtres';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/filtres';

    constructor(protected http: HttpClient) {}

    create(filtre: IFiltre): Observable<EntityResponseType> {
        return this.http.post<IFiltre>(this.resourceUrl, filtre, { observe: 'response' });
    }

    update(filtre: IFiltre): Observable<EntityResponseType> {
        return this.http.put<IFiltre>(this.resourceUrl, filtre, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFiltre>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFiltre[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFiltre[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
