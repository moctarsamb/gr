import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChampsRecherche } from 'app/shared/model/champs-recherche.model';

type EntityResponseType = HttpResponse<IChampsRecherche>;
type EntityArrayResponseType = HttpResponse<IChampsRecherche[]>;

@Injectable({ providedIn: 'root' })
export class ChampsRechercheService {
    public resourceUrl = SERVER_API_URL + 'api/champs-recherches';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/champs-recherches';

    constructor(protected http: HttpClient) {}

    create(champsRecherche: IChampsRecherche): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(champsRecherche);
        return this.http
            .post<IChampsRecherche>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(champsRecherche: IChampsRecherche): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(champsRecherche);
        return this.http
            .put<IChampsRecherche>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IChampsRecherche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IChampsRecherche[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IChampsRecherche[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(champsRecherche: IChampsRecherche): IChampsRecherche {
        const copy: IChampsRecherche = Object.assign({}, champsRecherche, {
            dateDebutExtraction:
                champsRecherche.dateDebutExtraction != null && champsRecherche.dateDebutExtraction.isValid()
                    ? champsRecherche.dateDebutExtraction.format(DATE_FORMAT)
                    : null,
            dateFinExtraction:
                champsRecherche.dateFinExtraction != null && champsRecherche.dateFinExtraction.isValid()
                    ? champsRecherche.dateFinExtraction.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateDebutExtraction = res.body.dateDebutExtraction != null ? moment(res.body.dateDebutExtraction) : null;
            res.body.dateFinExtraction = res.body.dateFinExtraction != null ? moment(res.body.dateFinExtraction) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((champsRecherche: IChampsRecherche) => {
                champsRecherche.dateDebutExtraction =
                    champsRecherche.dateDebutExtraction != null ? moment(champsRecherche.dateDebutExtraction) : null;
                champsRecherche.dateFinExtraction =
                    champsRecherche.dateFinExtraction != null ? moment(champsRecherche.dateFinExtraction) : null;
            });
        }
        return res;
    }
}
