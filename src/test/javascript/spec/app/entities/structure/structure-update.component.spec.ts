/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { StructureUpdateComponent } from 'app/entities/structure/structure-update.component';
import { StructureService } from 'app/entities/structure/structure.service';
import { Structure } from 'app/shared/model/structure.model';

describe('Component Tests', () => {
    describe('Structure Management Update Component', () => {
        let comp: StructureUpdateComponent;
        let fixture: ComponentFixture<StructureUpdateComponent>;
        let service: StructureService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [StructureUpdateComponent]
            })
                .overrideTemplate(StructureUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StructureUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StructureService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Structure(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.structure = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Structure();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.structure = entity;
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
