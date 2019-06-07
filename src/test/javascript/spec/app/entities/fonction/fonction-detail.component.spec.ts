/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FonctionDetailComponent } from 'app/entities/fonction/fonction-detail.component';
import { Fonction } from 'app/shared/model/fonction.model';

describe('Component Tests', () => {
    describe('Fonction Management Detail Component', () => {
        let comp: FonctionDetailComponent;
        let fixture: ComponentFixture<FonctionDetailComponent>;
        const route = ({ data: of({ fonction: new Fonction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FonctionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FonctionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FonctionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fonction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
