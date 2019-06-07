/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { MonitorDetailComponent } from 'app/entities/monitor/monitor-detail.component';
import { Monitor } from 'app/shared/model/monitor.model';

describe('Component Tests', () => {
    describe('Monitor Management Detail Component', () => {
        let comp: MonitorDetailComponent;
        let fixture: ComponentFixture<MonitorDetailComponent>;
        const route = ({ data: of({ monitor: new Monitor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [MonitorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MonitorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MonitorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.monitor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
