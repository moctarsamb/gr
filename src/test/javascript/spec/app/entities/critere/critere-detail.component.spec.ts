/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { CritereDetailComponent } from 'app/entities/critere/critere-detail.component';
import { Critere } from 'app/shared/model/critere.model';

describe('Component Tests', () => {
    describe('Critere Management Detail Component', () => {
        let comp: CritereDetailComponent;
        let fixture: ComponentFixture<CritereDetailComponent>;
        const route = ({ data: of({ critere: new Critere(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [CritereDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CritereDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CritereDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.critere).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
