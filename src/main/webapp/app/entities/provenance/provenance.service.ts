import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProvenance } from 'app/shared/model/provenance.model';

type EntityResponseType = HttpResponse<IProvenance>;
type EntityArrayResponseType = HttpResponse<IProvenance[]>;

@Injectable({ providedIn: 'root' })
export class ProvenanceService {
    public resourceUrl = SERVER_API_URL + 'api/provenances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/provenances';

    constructor(protected http: HttpClient) {}

    create(provenance: IProvenance): Observable<EntityResponseType> {
        return this.http.post<IProvenance>(this.resourceUrl, provenance, { observe: 'response' });
    }

    update(provenance: IProvenance): Observable<EntityResponseType> {
        return this.http.put<IProvenance>(this.resourceUrl, provenance, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProvenance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProvenance[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProvenance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
