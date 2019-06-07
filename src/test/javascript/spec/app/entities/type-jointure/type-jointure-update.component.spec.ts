/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypeJointureUpdateComponent } from 'app/entities/type-jointure/type-jointure-update.component';
import { TypeJointureService } from 'app/entities/type-jointure/type-jointure.service';
import { TypeJointure } from 'app/shared/model/type-jointure.model';

describe('Component Tests', () => {
    describe('TypeJointure Management Update Component', () => {
        let comp: TypeJointureUpdateComponent;
        let fixture: ComponentFixture<TypeJointureUpdateComponent>;
        let service: TypeJointureService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypeJointureUpdateComponent]
            })
                .overrideTemplate(TypeJointureUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeJointureUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeJointureService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeJointure(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeJointure = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeJointure();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeJointure = entity;
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
