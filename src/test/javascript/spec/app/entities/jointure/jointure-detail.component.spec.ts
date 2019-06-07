/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { JointureDetailComponent } from 'app/entities/jointure/jointure-detail.component';
import { Jointure } from 'app/shared/model/jointure.model';

describe('Component Tests', () => {
    describe('Jointure Management Detail Component', () => {
        let comp: JointureDetailComponent;
        let fixture: ComponentFixture<JointureDetailComponent>;
        const route = ({ data: of({ jointure: new Jointure(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [JointureDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JointureDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JointureDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jointure).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
