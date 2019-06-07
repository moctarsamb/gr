/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FiltreDetailComponent } from 'app/entities/filtre/filtre-detail.component';
import { Filtre } from 'app/shared/model/filtre.model';

describe('Component Tests', () => {
    describe('Filtre Management Detail Component', () => {
        let comp: FiltreDetailComponent;
        let fixture: ComponentFixture<FiltreDetailComponent>;
        const route = ({ data: of({ filtre: new Filtre(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FiltreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FiltreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FiltreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.filtre).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
