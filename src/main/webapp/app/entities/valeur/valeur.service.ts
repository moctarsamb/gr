import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IValeur } from 'app/shared/model/valeur.model';

type EntityResponseType = HttpResponse<IValeur>;
type EntityArrayResponseType = HttpResponse<IValeur[]>;

@Injectable({ providedIn: 'root' })
export class ValeurService {
    public resourceUrl = SERVER_API_URL + 'api/valeurs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/valeurs';

    constructor(protected http: HttpClient) {}

    create(valeur: IValeur): Observable<EntityResponseType> {
        return this.http.post<IValeur>(this.resourceUrl, valeur, { observe: 'response' });
    }

    update(valeur: IValeur): Observable<EntityResponseType> {
        return this.http.put<IValeur>(this.resourceUrl, valeur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IValeur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IValeur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IValeur[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
