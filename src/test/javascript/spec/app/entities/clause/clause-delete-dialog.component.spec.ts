/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ClauseDeleteDialogComponent } from 'app/entities/clause/clause-delete-dialog.component';
import { ClauseService } from 'app/entities/clause/clause.service';

describe('Component Tests', () => {
    describe('Clause Management Delete Component', () => {
        let comp: ClauseDeleteDialogComponent;
        let fixture: ComponentFixture<ClauseDeleteDialogComponent>;
        let service: ClauseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ClauseDeleteDialogComponent]
            })
                .overrideTemplate(ClauseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClauseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClauseService);
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
