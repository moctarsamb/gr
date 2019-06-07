/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypologieUpdateComponent } from 'app/entities/typologie/typologie-update.component';
import { TypologieService } from 'app/entities/typologie/typologie.service';
import { Typologie } from 'app/shared/model/typologie.model';

describe('Component Tests', () => {
    describe('Typologie Management Update Component', () => {
        let comp: TypologieUpdateComponent;
        let fixture: ComponentFixture<TypologieUpdateComponent>;
        let service: TypologieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypologieUpdateComponent]
            })
                .overrideTemplate(TypologieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypologieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypologieService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Typologie(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typologie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Typologie();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typologie = entity;
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
