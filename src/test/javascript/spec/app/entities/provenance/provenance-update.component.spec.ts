/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ProvenanceUpdateComponent } from 'app/entities/provenance/provenance-update.component';
import { ProvenanceService } from 'app/entities/provenance/provenance.service';
import { Provenance } from 'app/shared/model/provenance.model';

describe('Component Tests', () => {
    describe('Provenance Management Update Component', () => {
        let comp: ProvenanceUpdateComponent;
        let fixture: ComponentFixture<ProvenanceUpdateComponent>;
        let service: ProvenanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ProvenanceUpdateComponent]
            })
                .overrideTemplate(ProvenanceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProvenanceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvenanceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Provenance(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.provenance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Provenance();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.provenance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
