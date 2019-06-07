/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypeJointureDetailComponent } from 'app/entities/type-jointure/type-jointure-detail.component';
import { TypeJointure } from 'app/shared/model/type-jointure.model';

describe('Component Tests', () => {
    describe('TypeJointure Management Detail Component', () => {
        let comp: TypeJointureDetailComponent;
        let fixture: ComponentFixture<TypeJointureDetailComponent>;
        const route = ({ data: of({ typeJointure: new TypeJointure(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypeJointureDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeJointureDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeJointureDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeJointure).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
