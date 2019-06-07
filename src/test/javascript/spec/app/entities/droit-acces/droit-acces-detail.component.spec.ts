/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { DroitAccesDetailComponent } from 'app/entities/droit-acces/droit-acces-detail.component';
import { DroitAcces } from 'app/shared/model/droit-acces.model';

describe('Component Tests', () => {
    describe('DroitAcces Management Detail Component', () => {
        let comp: DroitAccesDetailComponent;
        let fixture: ComponentFixture<DroitAccesDetailComponent>;
        const route = ({ data: of({ droitAcces: new DroitAcces(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [DroitAccesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DroitAccesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DroitAccesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.droitAcces).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
