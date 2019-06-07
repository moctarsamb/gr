/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ClauseUpdateComponent } from 'app/entities/clause/clause-update.component';
import { ClauseService } from 'app/entities/clause/clause.service';
import { Clause } from 'app/shared/model/clause.model';

describe('Component Tests', () => {
    describe('Clause Management Update Component', () => {
        let comp: ClauseUpdateComponent;
        let fixture: ComponentFixture<ClauseUpdateComponent>;
        let service: ClauseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ClauseUpdateComponent]
            })
                .overrideTemplate(ClauseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClauseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClauseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Clause(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.clause = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Clause();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.clause = entity;
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
