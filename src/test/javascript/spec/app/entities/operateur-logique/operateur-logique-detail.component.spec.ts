/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { OperateurLogiqueDetailComponent } from 'app/entities/operateur-logique/operateur-logique-detail.component';
import { OperateurLogique } from 'app/shared/model/operateur-logique.model';

describe('Component Tests', () => {
    describe('OperateurLogique Management Detail Component', () => {
        let comp: OperateurLogiqueDetailComponent;
        let fixture: ComponentFixture<OperateurLogiqueDetailComponent>;
        const route = ({ data: of({ operateurLogique: new OperateurLogique(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [OperateurLogiqueDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OperateurLogiqueDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperateurLogiqueDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.operateurLogique).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
