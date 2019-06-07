/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ParametrageApplicationUpdateComponent } from 'app/entities/parametrage-application/parametrage-application-update.component';
import { ParametrageApplicationService } from 'app/entities/parametrage-application/parametrage-application.service';
import { ParametrageApplication } from 'app/shared/model/parametrage-application.model';

describe('Component Tests', () => {
    describe('ParametrageApplication Management Update Component', () => {
        let comp: ParametrageApplicationUpdateComponent;
        let fixture: ComponentFixture<ParametrageApplicationUpdateComponent>;
        let service: ParametrageApplicationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ParametrageApplicationUpdateComponent]
            })
                .overrideTemplate(ParametrageApplicationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParametrageApplicationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParametrageApplicationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ParametrageApplication(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.parametrageApplication = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ParametrageApplication();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.parametrageApplication = entity;
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
