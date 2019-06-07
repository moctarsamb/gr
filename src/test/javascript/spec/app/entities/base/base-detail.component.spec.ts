/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { BaseDetailComponent } from 'app/entities/base/base-detail.component';
import { Base } from 'app/shared/model/base.model';

describe('Component Tests', () => {
    describe('Base Management Detail Component', () => {
        let comp: BaseDetailComponent;
        let fixture: ComponentFixture<BaseDetailComponent>;
        const route = ({ data: of({ base: new Base(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [BaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.base).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
