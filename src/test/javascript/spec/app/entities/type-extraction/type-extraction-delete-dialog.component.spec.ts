/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypeExtractionDeleteDialogComponent } from 'app/entities/type-extraction/type-extraction-delete-dialog.component';
import { TypeExtractionService } from 'app/entities/type-extraction/type-extraction.service';

describe('Component Tests', () => {
    describe('TypeExtraction Management Delete Component', () => {
        let comp: TypeExtractionDeleteDialogComponent;
        let fixture: ComponentFixture<TypeExtractionDeleteDialogComponent>;
        let service: TypeExtractionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypeExtractionDeleteDialogComponent]
            })
                .overrideTemplate(TypeExtractionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeExtractionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeExtractionService);
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
