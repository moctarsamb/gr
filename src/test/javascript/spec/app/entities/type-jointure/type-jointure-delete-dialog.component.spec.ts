/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypeJointureDeleteDialogComponent } from 'app/entities/type-jointure/type-jointure-delete-dialog.component';
import { TypeJointureService } from 'app/entities/type-jointure/type-jointure.service';

describe('Component Tests', () => {
    describe('TypeJointure Management Delete Component', () => {
        let comp: TypeJointureDeleteDialogComponent;
        let fixture: ComponentFixture<TypeJointureDeleteDialogComponent>;
        let service: TypeJointureService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypeJointureDeleteDialogComponent]
            })
                .overrideTemplate(TypeJointureDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeJointureDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeJointureService);
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
