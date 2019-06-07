/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ParametrageApplicationDetailComponent } from 'app/entities/parametrage-application/parametrage-application-detail.component';
import { ParametrageApplication } from 'app/shared/model/parametrage-application.model';

describe('Component Tests', () => {
    describe('ParametrageApplication Management Detail Component', () => {
        let comp: ParametrageApplicationDetailComponent;
        let fixture: ComponentFixture<ParametrageApplicationDetailComponent>;
        const route = ({ data: of({ parametrageApplication: new ParametrageApplication(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ParametrageApplicationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParametrageApplicationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParametrageApplicationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parametrageApplication).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
