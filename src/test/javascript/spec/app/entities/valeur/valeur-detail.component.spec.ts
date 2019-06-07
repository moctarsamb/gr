/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ValeurDetailComponent } from 'app/entities/valeur/valeur-detail.component';
import { Valeur } from 'app/shared/model/valeur.model';

describe('Component Tests', () => {
    describe('Valeur Management Detail Component', () => {
        let comp: ValeurDetailComponent;
        let fixture: ComponentFixture<ValeurDetailComponent>;
        const route = ({ data: of({ valeur: new Valeur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ValeurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ValeurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ValeurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.valeur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
