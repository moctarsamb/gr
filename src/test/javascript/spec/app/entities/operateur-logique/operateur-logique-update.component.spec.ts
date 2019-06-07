/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { OperateurLogiqueUpdateComponent } from 'app/entities/operateur-logique/operateur-logique-update.component';
import { OperateurLogiqueService } from 'app/entities/operateur-logique/operateur-logique.service';
import { OperateurLogique } from 'app/shared/model/operateur-logique.model';

describe('Component Tests', () => {
    describe('OperateurLogique Management Update Component', () => {
        let comp: OperateurLogiqueUpdateComponent;
        let fixture: ComponentFixture<OperateurLogiqueUpdateComponent>;
        let service: OperateurLogiqueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [OperateurLogiqueUpdateComponent]
            })
                .overrideTemplate(OperateurLogiqueUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OperateurLogiqueUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperateurLogiqueService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new OperateurLogique(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.operateurLogique = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new OperateurLogique();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.operateurLogique = entity;
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
