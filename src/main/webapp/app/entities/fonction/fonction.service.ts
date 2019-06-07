import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFonction } from 'app/shared/model/fonction.model';

type EntityResponseType = HttpResponse<IFonction>;
type EntityArrayResponseType = HttpResponse<IFonction[]>;

@Injectable({ providedIn: 'root' })
export class FonctionService {
    public resourceUrl = SERVER_API_URL + 'api/fonctions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/fonctions';

    constructor(protected http: HttpClient) {}

    create(fonction: IFonction): Observable<EntityResponseType> {
        return this.http.post<IFonction>(this.resourceUrl, fonction, { observe: 'response' });
    }

    update(fonction: IFonction): Observable<EntityResponseType> {
        return this.http.put<IFonction>(this.resourceUrl, fonction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFonction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFonction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFonction[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
