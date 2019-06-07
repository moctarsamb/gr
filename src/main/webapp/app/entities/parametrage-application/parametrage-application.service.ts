import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParametrageApplication } from 'app/shared/model/parametrage-application.model';

type EntityResponseType = HttpResponse<IParametrageApplication>;
type EntityArrayResponseType = HttpResponse<IParametrageApplication[]>;

@Injectable({ providedIn: 'root' })
export class ParametrageApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/parametrage-applications';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/parametrage-applications';

    constructor(protected http: HttpClient) {}

    create(parametrageApplication: IParametrageApplication): Observable<EntityResponseType> {
        return this.http.post<IParametrageApplication>(this.resourceUrl, parametrageApplication, { observe: 'response' });
    }

    update(parametrageApplication: IParametrageApplication): Observable<EntityResponseType> {
        return this.http.put<IParametrageApplication>(this.resourceUrl, parametrageApplication, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParametrageApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParametrageApplication[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParametrageApplication[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
