/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { JointureUpdateComponent } from 'app/entities/jointure/jointure-update.component';
import { JointureService } from 'app/entities/jointure/jointure.service';
import { Jointure } from 'app/shared/model/jointure.model';

describe('Component Tests', () => {
    describe('Jointure Management Update Component', () => {
        let comp: JointureUpdateComponent;
        let fixture: ComponentFixture<JointureUpdateComponent>;
        let service: JointureService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [JointureUpdateComponent]
            })
                .overrideTemplate(JointureUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JointureUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JointureService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Jointure(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.jointure = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Jointure();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.jointure = entity;
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
