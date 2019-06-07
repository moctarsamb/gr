/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypologieDetailComponent } from 'app/entities/typologie/typologie-detail.component';
import { Typologie } from 'app/shared/model/typologie.model';

describe('Component Tests', () => {
    describe('Typologie Management Detail Component', () => {
        let comp: TypologieDetailComponent;
        let fixture: ComponentFixture<TypologieDetailComponent>;
        const route = ({ data: of({ typologie: new Typologie(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypologieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypologieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypologieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typologie).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
