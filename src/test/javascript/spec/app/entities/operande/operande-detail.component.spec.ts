/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { OperandeDetailComponent } from 'app/entities/operande/operande-detail.component';
import { Operande } from 'app/shared/model/operande.model';

describe('Component Tests', () => {
    describe('Operande Management Detail Component', () => {
        let comp: OperandeDetailComponent;
        let fixture: ComponentFixture<OperandeDetailComponent>;
        const route = ({ data: of({ operande: new Operande(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [OperandeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OperandeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperandeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.operande).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
