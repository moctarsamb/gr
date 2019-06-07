/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { EnvoiResultatUpdateComponent } from 'app/entities/envoi-resultat/envoi-resultat-update.component';
import { EnvoiResultatService } from 'app/entities/envoi-resultat/envoi-resultat.service';
import { EnvoiResultat } from 'app/shared/model/envoi-resultat.model';

describe('Component Tests', () => {
    describe('EnvoiResultat Management Update Component', () => {
        let comp: EnvoiResultatUpdateComponent;
        let fixture: ComponentFixture<EnvoiResultatUpdateComponent>;
        let service: EnvoiResultatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [EnvoiResultatUpdateComponent]
            })
                .overrideTemplate(EnvoiResultatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvoiResultatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvoiResultatService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EnvoiResultat(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.envoiResultat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EnvoiResultat();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.envoiResultat = entity;
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
