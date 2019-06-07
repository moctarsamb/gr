/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { JointureDeleteDialogComponent } from 'app/entities/jointure/jointure-delete-dialog.component';
import { JointureService } from 'app/entities/jointure/jointure.service';

describe('Component Tests', () => {
    describe('Jointure Management Delete Component', () => {
        let comp: JointureDeleteDialogComponent;
        let fixture: ComponentFixture<JointureDeleteDialogComponent>;
        let service: JointureService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [JointureDeleteDialogComponent]
            })
                .overrideTemplate(JointureDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JointureDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JointureService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
