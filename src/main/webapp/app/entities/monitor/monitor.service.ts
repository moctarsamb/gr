import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonitor } from 'app/shared/model/monitor.model';

type EntityResponseType = HttpResponse<IMonitor>;
type EntityArrayResponseType = HttpResponse<IMonitor[]>;

@Injectable({ providedIn: 'root' })
export class MonitorService {
    public resourceUrl = SERVER_API_URL + 'api/monitors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/monitors';

    constructor(protected http: HttpClient) {}

    create(monitor: IMonitor): Observable<EntityResponseType> {
        return this.http.post<IMonitor>(this.resourceUrl, monitor, { observe: 'response' });
    }

    update(monitor: IMonitor): Observable<EntityResponseType> {
        return this.http.put<IMonitor>(this.resourceUrl, monitor, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMonitor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMonitor[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMonitor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
