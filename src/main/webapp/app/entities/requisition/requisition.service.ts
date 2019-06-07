import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisition } from 'app/shared/model/requisition.model';

type EntityResponseType = HttpResponse<IRequisition>;
type EntityArrayResponseType = HttpResponse<IRequisition[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionService {
    public resourceUrl = SERVER_API_URL + 'api/requisitions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/requisitions';

    constructor(protected http: HttpClient) {}

    create(requisition: IRequisition): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisition);
        return this.http
            .post<IRequisition>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisition: IRequisition): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisition);
        return this.http
            .put<IRequisition>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRequisition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisition[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(requisition: IRequisition): IRequisition {
        const copy: IRequisition = Object.assign({}, requisition, {
            dateSaisiePv:
                requisition.dateSaisiePv != null && requisition.dateSaisiePv.isValid()
                    ? requisition.dateSaisiePv.format(DATE_FORMAT)
                    : null,
            dateArriveeDemande:
                requisition.dateArriveeDemande != null && requisition.dateArriveeDemande.isValid()
                    ? requisition.dateArriveeDemande.toJSON()
                    : null,
            dateSaisieDemande:
                requisition.dateSaisieDemande != null && requisition.dateSaisieDemande.isValid()
                    ? requisition.dateSaisieDemande.toJSON()
                    : null,
            dateReponse: requisition.dateReponse != null && requisition.dateReponse.isValid() ? requisition.dateReponse.toJSON() : null,
            dateRenvoieResultat:
                requisition.dateRenvoieResultat != null && requisition.dateRenvoieResultat.isValid()
                    ? requisition.dateRenvoieResultat.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateSaisiePv = res.body.dateSaisiePv != null ? moment(res.body.dateSaisiePv) : null;
            res.body.dateArriveeDemande = res.body.dateArriveeDemande != null ? moment(res.body.dateArriveeDemande) : null;
            res.body.dateSaisieDemande = res.body.dateSaisieDemande != null ? moment(res.body.dateSaisieDemande) : null;
            res.body.dateReponse = res.body.dateReponse != null ? moment(res.body.dateReponse) : null;
            res.body.dateRenvoieResultat = res.body.dateRenvoieResultat != null ? moment(res.body.dateRenvoieResultat) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((requisition: IRequisition) => {
                requisition.dateSaisiePv = requisition.dateSaisiePv != null ? moment(requisition.dateSaisiePv) : null;
                requisition.dateArriveeDemande = requisition.dateArriveeDemande != null ? moment(requisition.dateArriveeDemande) : null;
                requisition.dateSaisieDemande = requisition.dateSaisieDemande != null ? moment(requisition.dateSaisieDemande) : null;
                requisition.dateReponse = requisition.dateReponse != null ? moment(requisition.dateReponse) : null;
                requisition.dateRenvoieResultat = requisition.dateRenvoieResultat != null ? moment(requisition.dateRenvoieResultat) : null;
            });
        }
        return res;
    }
}
