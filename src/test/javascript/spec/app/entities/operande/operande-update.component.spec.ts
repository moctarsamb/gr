/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { OperandeUpdateComponent } from 'app/entities/operande/operande-update.component';
import { OperandeService } from 'app/entities/operande/operande.service';
import { Operande } from 'app/shared/model/operande.model';

describe('Component Tests', () => {
    describe('Operande Management Update Component', () => {
        let comp: OperandeUpdateComponent;
        let fixture: ComponentFixture<OperandeUpdateComponent>;
        let service: OperandeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [OperandeUpdateComponent]
            })
                .overrideTemplate(OperandeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OperandeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperandeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Operande(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.operande = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Operande();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.operande = entity;
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
