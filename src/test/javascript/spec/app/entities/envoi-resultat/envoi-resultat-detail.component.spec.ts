/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { EnvoiResultatDetailComponent } from 'app/entities/envoi-resultat/envoi-resultat-detail.component';
import { EnvoiResultat } from 'app/shared/model/envoi-resultat.model';

describe('Component Tests', () => {
    describe('EnvoiResultat Management Detail Component', () => {
        let comp: EnvoiResultatDetailComponent;
        let fixture: ComponentFixture<EnvoiResultatDetailComponent>;
        const route = ({ data: of({ envoiResultat: new EnvoiResultat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [EnvoiResultatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnvoiResultatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnvoiResultatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.envoiResultat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
