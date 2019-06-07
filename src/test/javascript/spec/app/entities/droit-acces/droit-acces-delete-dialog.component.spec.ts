/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { DroitAccesDeleteDialogComponent } from 'app/entities/droit-acces/droit-acces-delete-dialog.component';
import { DroitAccesService } from 'app/entities/droit-acces/droit-acces.service';

describe('Component Tests', () => {
    describe('DroitAcces Management Delete Component', () => {
        let comp: DroitAccesDeleteDialogComponent;
        let fixture: ComponentFixture<DroitAccesDeleteDialogComponent>;
        let service: DroitAccesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [DroitAccesDeleteDialogComponent]
            })
                .overrideTemplate(DroitAccesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DroitAccesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DroitAccesService);
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
