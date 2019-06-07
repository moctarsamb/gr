/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FonctionUpdateComponent } from 'app/entities/fonction/fonction-update.component';
import { FonctionService } from 'app/entities/fonction/fonction.service';
import { Fonction } from 'app/shared/model/fonction.model';

describe('Component Tests', () => {
    describe('Fonction Management Update Component', () => {
        let comp: FonctionUpdateComponent;
        let fixture: ComponentFixture<FonctionUpdateComponent>;
        let service: FonctionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FonctionUpdateComponent]
            })
                .overrideTemplate(FonctionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FonctionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FonctionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Fonction(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fonction = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Fonction();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fonction = entity;
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
