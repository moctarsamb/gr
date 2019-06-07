import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFichierResultat } from 'app/shared/model/fichier-resultat.model';

type EntityResponseType = HttpResponse<IFichierResultat>;
type EntityArrayResponseType = HttpResponse<IFichierResultat[]>;

@Injectable({ providedIn: 'root' })
export class FichierResultatService {
    public resourceUrl = SERVER_API_URL + 'api/fichier-resultats';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/fichier-resultats';

    constructor(protected http: HttpClient) {}

    create(fichierResultat: IFichierResultat): Observable<EntityResponseType> {
        return this.http.post<IFichierResultat>(this.resourceUrl, fichierResultat, { observe: 'response' });
    }

    update(fichierResultat: IFichierResultat): Observable<EntityResponseType> {
        return this.http.put<IFichierResultat>(this.resourceUrl, fichierResultat, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFichierResultat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFichierResultat[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFichierResultat[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
