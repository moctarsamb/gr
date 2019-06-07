/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RequisitionService } from 'app/entities/requisition/requisition.service';
import { IRequisition, Requisition, StatusEvolution, EtatRequisition } from 'app/shared/model/requisition.model';

describe('Service Tests', () => {
    describe('Requisition Service', () => {
        let injector: TestBed;
        let service: RequisitionService;
        let httpMock: HttpTestingController;
        let elemDefault: IRequisition;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RequisitionService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Requisition(
                0,
                0,
                0,
                currentDate,
                currentDate,
                currentDate,
                false,
                currentDate,
                currentDate,
                StatusEvolution.ENCOURS,
                EtatRequisition.ERREUR
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateSaisiePv: currentDate.format(DATE_FORMAT),
                        dateArriveeDemande: currentDate.format(DATE_TIME_FORMAT),
                        dateSaisieDemande: currentDate.format(DATE_TIME_FORMAT),
                        dateReponse: currentDate.format(DATE_TIME_FORMAT),
                        dateRenvoieResultat: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Requisition', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateSaisiePv: currentDate.format(DATE_FORMAT),
                        dateArriveeDemande: currentDate.format(DATE_TIME_FORMAT),
                        dateSaisieDemande: currentDate.format(DATE_TIME_FORMAT),
                        dateReponse: currentDate.format(DATE_TIME_FORMAT),
                        dateRenvoieResultat: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateSaisiePv: currentDate,
                        dateArriveeDemande: currentDate,
                        dateSaisieDemande: currentDate,
                        dateReponse: currentDate,
                        dateRenvoieResultat: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Requisition(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Requisition', async () => {
                const returnedFromService = Object.assign(
                    {
                        numeroArriveeDemande: 1,
                        numeroPv: 1,
                        dateSaisiePv: currentDate.format(DATE_FORMAT),
                        dateArriveeDemande: currentDate.format(DATE_TIME_FORMAT),
                        dateSaisieDemande: currentDate.format(DATE_TIME_FORMAT),
                        envoiResultatAutomatique: true,
                        dateReponse: currentDate.format(DATE_TIME_FORMAT),
                        dateRenvoieResultat: currentDate.format(DATE_TIME_FORMAT),
                        status: 'BBBBBB',
                        etat: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateSaisiePv: currentDate,
                        dateArriveeDemande: currentDate,
                        dateSaisieDemande: currentDate,
                        dateReponse: currentDate,
                        dateRenvoieResultat: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Requisition', async () => {
                const returnedFromService = Object.assign(
                    {
                        numeroArriveeDemande: 1,
                        numeroPv: 1,
                        dateSaisiePv: currentDate.format(DATE_FORMAT),
                        dateArriveeDemande: currentDate.format(DATE_TIME_FORMAT),
                        dateSaisieDemande: currentDate.format(DATE_TIME_FORMAT),
                        envoiResultatAutomatique: true,
                        dateReponse: currentDate.format(DATE_TIME_FORMAT),
                        dateRenvoieResultat: currentDate.format(DATE_TIME_FORMAT),
                        status: 'BBBBBB',
                        etat: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateSaisiePv: currentDate,
                        dateArriveeDemande: currentDate,
                        dateSaisieDemande: currentDate,
                        dateReponse: currentDate,
                        dateRenvoieResultat: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Requisition', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
