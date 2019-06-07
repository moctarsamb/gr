/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ResultatUpdateComponent } from 'app/entities/resultat/resultat-update.component';
import { ResultatService } from 'app/entities/resultat/resultat.service';
import { Resultat } from 'app/shared/model/resultat.model';

describe('Component Tests', () => {
    describe('Resultat Management Update Component', () => {
        let comp: ResultatUpdateComponent;
        let fixture: ComponentFixture<ResultatUpdateComponent>;
        let service: ResultatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ResultatUpdateComponent]
            })
                .overrideTemplate(ResultatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResultatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultatService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Resultat(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.resultat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Resultat();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.resultat = entity;
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
