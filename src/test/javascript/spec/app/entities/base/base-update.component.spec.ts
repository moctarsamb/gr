/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { BaseUpdateComponent } from 'app/entities/base/base-update.component';
import { BaseService } from 'app/entities/base/base.service';
import { Base } from 'app/shared/model/base.model';

describe('Component Tests', () => {
    describe('Base Management Update Component', () => {
        let comp: BaseUpdateComponent;
        let fixture: ComponentFixture<BaseUpdateComponent>;
        let service: BaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [BaseUpdateComponent]
            })
                .overrideTemplate(BaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Base(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.base = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Base();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.base = entity;
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
