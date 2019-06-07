/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ResultatDetailComponent } from 'app/entities/resultat/resultat-detail.component';
import { Resultat } from 'app/shared/model/resultat.model';

describe('Component Tests', () => {
    describe('Resultat Management Detail Component', () => {
        let comp: ResultatDetailComponent;
        let fixture: ComponentFixture<ResultatDetailComponent>;
        const route = ({ data: of({ resultat: new Resultat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ResultatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResultatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resultat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
