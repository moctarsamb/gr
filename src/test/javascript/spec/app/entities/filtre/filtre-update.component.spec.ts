/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FiltreUpdateComponent } from 'app/entities/filtre/filtre-update.component';
import { FiltreService } from 'app/entities/filtre/filtre.service';
import { Filtre } from 'app/shared/model/filtre.model';

describe('Component Tests', () => {
    describe('Filtre Management Update Component', () => {
        let comp: FiltreUpdateComponent;
        let fixture: ComponentFixture<FiltreUpdateComponent>;
        let service: FiltreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FiltreUpdateComponent]
            })
                .overrideTemplate(FiltreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FiltreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FiltreService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Filtre(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.filtre = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Filtre();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.filtre = entity;
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
