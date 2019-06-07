/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FilialeDetailComponent } from 'app/entities/filiale/filiale-detail.component';
import { Filiale } from 'app/shared/model/filiale.model';

describe('Component Tests', () => {
    describe('Filiale Management Detail Component', () => {
        let comp: FilialeDetailComponent;
        let fixture: ComponentFixture<FilialeDetailComponent>;
        const route = ({ data: of({ filiale: new Filiale(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FilialeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FilialeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FilialeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.filiale).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
