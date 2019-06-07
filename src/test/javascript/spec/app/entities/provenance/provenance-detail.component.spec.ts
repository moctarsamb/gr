/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ProvenanceDetailComponent } from 'app/entities/provenance/provenance-detail.component';
import { Provenance } from 'app/shared/model/provenance.model';

describe('Component Tests', () => {
    describe('Provenance Management Detail Component', () => {
        let comp: ProvenanceDetailComponent;
        let fixture: ComponentFixture<ProvenanceDetailComponent>;
        const route = ({ data: of({ provenance: new Provenance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ProvenanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProvenanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProvenanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.provenance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
