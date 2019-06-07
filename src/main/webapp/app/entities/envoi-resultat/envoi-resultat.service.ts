import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnvoiResultat } from 'app/shared/model/envoi-resultat.model';

type EntityResponseType = HttpResponse<IEnvoiResultat>;
type EntityArrayResponseType = HttpResponse<IEnvoiResultat[]>;

@Injectable({ providedIn: 'root' })
export class EnvoiResultatService {
    public resourceUrl = SERVER_API_URL + 'api/envoi-resultats';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/envoi-resultats';

    constructor(protected http: HttpClient) {}

    create(envoiResultat: IEnvoiResultat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(envoiResultat);
        return this.http
            .post<IEnvoiResultat>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(envoiResultat: IEnvoiResultat): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(envoiResultat);
        return this.http
            .put<IEnvoiResultat>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEnvoiResultat>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEnvoiResultat[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEnvoiResultat[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(envoiResultat: IEnvoiResultat): IEnvoiResultat {
        const copy: IEnvoiResultat = Object.assign({}, envoiResultat, {
            dateEnvoi: envoiResultat.dateEnvoi != null && envoiResultat.dateEnvoi.isValid() ? envoiResultat.dateEnvoi.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateEnvoi = res.body.dateEnvoi != null ? moment(res.body.dateEnvoi) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((envoiResultat: IEnvoiResultat) => {
                envoiResultat.dateEnvoi = envoiResultat.dateEnvoi != null ? moment(envoiResultat.dateEnvoi) : null;
            });
        }
        return res;
    }
}
