/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ChampsRechercheDeleteDialogComponent } from 'app/entities/champs-recherche/champs-recherche-delete-dialog.component';
import { ChampsRechercheService } from 'app/entities/champs-recherche/champs-recherche.service';

describe('Component Tests', () => {
    describe('ChampsRecherche Management Delete Component', () => {
        let comp: ChampsRechercheDeleteDialogComponent;
        let fixture: ComponentFixture<ChampsRechercheDeleteDialogComponent>;
        let service: ChampsRechercheService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ChampsRechercheDeleteDialogComponent]
            })
                .overrideTemplate(ChampsRechercheDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChampsRechercheDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChampsRechercheService);
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
