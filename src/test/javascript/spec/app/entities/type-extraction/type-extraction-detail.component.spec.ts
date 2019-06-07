/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypeExtractionDetailComponent } from 'app/entities/type-extraction/type-extraction-detail.component';
import { TypeExtraction } from 'app/shared/model/type-extraction.model';

describe('Component Tests', () => {
    describe('TypeExtraction Management Detail Component', () => {
        let comp: TypeExtractionDetailComponent;
        let fixture: ComponentFixture<TypeExtractionDetailComponent>;
        const route = ({ data: of({ typeExtraction: new TypeExtraction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypeExtractionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeExtractionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeExtractionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeExtraction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
