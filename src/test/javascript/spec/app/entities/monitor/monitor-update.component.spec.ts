/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { MonitorUpdateComponent } from 'app/entities/monitor/monitor-update.component';
import { MonitorService } from 'app/entities/monitor/monitor.service';
import { Monitor } from 'app/shared/model/monitor.model';

describe('Component Tests', () => {
    describe('Monitor Management Update Component', () => {
        let comp: MonitorUpdateComponent;
        let fixture: ComponentFixture<MonitorUpdateComponent>;
        let service: MonitorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [MonitorUpdateComponent]
            })
                .overrideTemplate(MonitorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MonitorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MonitorService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Monitor(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.monitor = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Monitor();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.monitor = entity;
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
