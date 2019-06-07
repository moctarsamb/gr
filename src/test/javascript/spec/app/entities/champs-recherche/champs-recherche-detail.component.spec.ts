/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ChampsRechercheDetailComponent } from 'app/entities/champs-recherche/champs-recherche-detail.component';
import { ChampsRecherche } from 'app/shared/model/champs-recherche.model';

describe('Component Tests', () => {
    describe('ChampsRecherche Management Detail Component', () => {
        let comp: ChampsRechercheDetailComponent;
        let fixture: ComponentFixture<ChampsRechercheDetailComponent>;
        const route = ({ data: of({ champsRecherche: new ChampsRecherche(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ChampsRechercheDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChampsRechercheDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChampsRechercheDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.champsRecherche).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
