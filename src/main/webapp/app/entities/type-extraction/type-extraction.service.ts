import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeExtraction } from 'app/shared/model/type-extraction.model';

type EntityResponseType = HttpResponse<ITypeExtraction>;
type EntityArrayResponseType = HttpResponse<ITypeExtraction[]>;

@Injectable({ providedIn: 'root' })
export class TypeExtractionService {
    public resourceUrl = SERVER_API_URL + 'api/type-extractions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-extractions';

    constructor(protected http: HttpClient) {}

    create(typeExtraction: ITypeExtraction): Observable<EntityResponseType> {
        return this.http.post<ITypeExtraction>(this.resourceUrl, typeExtraction, { observe: 'response' });
    }

    update(typeExtraction: ITypeExtraction): Observable<EntityResponseType> {
        return this.http.put<ITypeExtraction>(this.resourceUrl, typeExtraction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeExtraction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeExtraction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeExtraction[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
