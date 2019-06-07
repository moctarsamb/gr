/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ChampsRechercheService } from 'app/entities/champs-recherche/champs-recherche.service';
import { IChampsRecherche, ChampsRecherche } from 'app/shared/model/champs-recherche.model';

describe('Service Tests', () => {
    describe('ChampsRecherche Service', () => {
        let injector: TestBed;
        let service: ChampsRechercheService;
        let httpMock: HttpTestingController;
        let elemDefault: IChampsRecherche;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ChampsRechercheService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ChampsRecherche(0, 'AAAAAAA', currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebutExtraction: currentDate.format(DATE_FORMAT),
                        dateFinExtraction: currentDate.format(DATE_FORMAT)
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

            it('should create a ChampsRecherche', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateDebutExtraction: currentDate.format(DATE_FORMAT),
                        dateFinExtraction: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebutExtraction: currentDate,
                        dateFinExtraction: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ChampsRecherche(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ChampsRecherche', async () => {
                const returnedFromService = Object.assign(
                    {
                        champs: 'BBBBBB',
                        dateDebutExtraction: currentDate.format(DATE_FORMAT),
                        dateFinExtraction: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateDebutExtraction: currentDate,
                        dateFinExtraction: currentDate
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

            it('should return a list of ChampsRecherche', async () => {
                const returnedFromService = Object.assign(
                    {
                        champs: 'BBBBBB',
                        dateDebutExtraction: currentDate.format(DATE_FORMAT),
                        dateFinExtraction: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebutExtraction: currentDate,
                        dateFinExtraction: currentDate
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

            it('should delete a ChampsRecherche', async () => {
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
