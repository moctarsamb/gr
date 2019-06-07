/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ClauseDetailComponent } from 'app/entities/clause/clause-detail.component';
import { Clause } from 'app/shared/model/clause.model';

describe('Component Tests', () => {
    describe('Clause Management Detail Component', () => {
        let comp: ClauseDetailComponent;
        let fixture: ComponentFixture<ClauseDetailComponent>;
        const route = ({ data: of({ clause: new Clause(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ClauseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClauseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClauseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clause).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
