/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { DroitAccesUpdateComponent } from 'app/entities/droit-acces/droit-acces-update.component';
import { DroitAccesService } from 'app/entities/droit-acces/droit-acces.service';
import { DroitAcces } from 'app/shared/model/droit-acces.model';

describe('Component Tests', () => {
    describe('DroitAcces Management Update Component', () => {
        let comp: DroitAccesUpdateComponent;
        let fixture: ComponentFixture<DroitAccesUpdateComponent>;
        let service: DroitAccesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [DroitAccesUpdateComponent]
            })
                .overrideTemplate(DroitAccesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DroitAccesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DroitAccesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DroitAcces(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.droitAcces = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DroitAcces();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.droitAcces = entity;
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
