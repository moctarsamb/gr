/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { StructureDetailComponent } from 'app/entities/structure/structure-detail.component';
import { Structure } from 'app/shared/model/structure.model';

describe('Component Tests', () => {
    describe('Structure Management Detail Component', () => {
        let comp: StructureDetailComponent;
        let fixture: ComponentFixture<StructureDetailComponent>;
        const route = ({ data: of({ structure: new Structure(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [StructureDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StructureDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StructureDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.structure).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
