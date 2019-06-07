/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { OperateurLogiqueDeleteDialogComponent } from 'app/entities/operateur-logique/operateur-logique-delete-dialog.component';
import { OperateurLogiqueService } from 'app/entities/operateur-logique/operateur-logique.service';

describe('Component Tests', () => {
    describe('OperateurLogique Management Delete Component', () => {
        let comp: OperateurLogiqueDeleteDialogComponent;
        let fixture: ComponentFixture<OperateurLogiqueDeleteDialogComponent>;
        let service: OperateurLogiqueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [OperateurLogiqueDeleteDialogComponent]
            })
                .overrideTemplate(OperateurLogiqueDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperateurLogiqueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperateurLogiqueService);
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
