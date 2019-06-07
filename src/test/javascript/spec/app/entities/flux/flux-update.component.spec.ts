/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FluxUpdateComponent } from 'app/entities/flux/flux-update.component';
import { FluxService } from 'app/entities/flux/flux.service';
import { Flux } from 'app/shared/model/flux.model';

describe('Component Tests', () => {
    describe('Flux Management Update Component', () => {
        let comp: FluxUpdateComponent;
        let fixture: ComponentFixture<FluxUpdateComponent>;
        let service: FluxService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FluxUpdateComponent]
            })
                .overrideTemplate(FluxUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FluxUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FluxService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Flux(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.flux = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Flux();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.flux = entity;
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
