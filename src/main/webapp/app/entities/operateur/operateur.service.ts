import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperateur } from 'app/shared/model/operateur.model';

type EntityResponseType = HttpResponse<IOperateur>;
type EntityArrayResponseType = HttpResponse<IOperateur[]>;

@Injectable({ providedIn: 'root' })
export class OperateurService {
    public resourceUrl = SERVER_API_URL + 'api/operateurs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/operateurs';

    constructor(protected http: HttpClient) {}

    create(operateur: IOperateur): Observable<EntityResponseType> {
        return this.http.post<IOperateur>(this.resourceUrl, operateur, { observe: 'response' });
    }

    update(operateur: IOperateur): Observable<EntityResponseType> {
        return this.http.put<IOperateur>(this.resourceUrl, operateur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOperateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperateur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperateur[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
