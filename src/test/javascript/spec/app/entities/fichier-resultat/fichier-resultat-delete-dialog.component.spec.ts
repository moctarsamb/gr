/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FichierResultatDeleteDialogComponent } from 'app/entities/fichier-resultat/fichier-resultat-delete-dialog.component';
import { FichierResultatService } from 'app/entities/fichier-resultat/fichier-resultat.service';

describe('Component Tests', () => {
    describe('FichierResultat Management Delete Component', () => {
        let comp: FichierResultatDeleteDialogComponent;
        let fixture: ComponentFixture<FichierResultatDeleteDialogComponent>;
        let service: FichierResultatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FichierResultatDeleteDialogComponent]
            })
                .overrideTemplate(FichierResultatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FichierResultatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FichierResultatService);
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
