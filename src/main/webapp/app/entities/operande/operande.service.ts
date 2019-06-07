import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperande } from 'app/shared/model/operande.model';

type EntityResponseType = HttpResponse<IOperande>;
type EntityArrayResponseType = HttpResponse<IOperande[]>;

@Injectable({ providedIn: 'root' })
export class OperandeService {
    public resourceUrl = SERVER_API_URL + 'api/operandes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/operandes';

    constructor(protected http: HttpClient) {}

    create(operande: IOperande): Observable<EntityResponseType> {
        return this.http.post<IOperande>(this.resourceUrl, operande, { observe: 'response' });
    }

    update(operande: IOperande): Observable<EntityResponseType> {
        return this.http.put<IOperande>(this.resourceUrl, operande, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOperande>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperande[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOperande[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
