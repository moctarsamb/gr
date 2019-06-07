import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperateurLogique } from 'app/shared/model/operateur-logique.model';

type EntityResponseType = HttpResponse<IOperateurLogique>;
type EntityArrayResponseType = HttpResponse<IOperateurLogique[]>;

@Injectable({ providedIn: 'root' })
export class OperateurLogiqueService {
    public resourceUrl = SERVER_API_URL + 'api/operateur-logiques';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/operateur-logiques';

    constructor(protected http: HttpClient) {}

    create(operateurLogique: IOperateurLogique): Observable<EntityResponseType> {
        return this.http.post<IOperateurLogique>(this.resourceUrl, operateurLogique, { observe: 'response' });
    }

    update(operateurLogique: IOperateurLogique): Observable<EntityResponseType> {
        return this.http.put<IOperateurLogique>(this.resourceUrl, operateurLogique, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOperateurLogique>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperateurLogique[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperateurLogique[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
