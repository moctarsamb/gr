/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FichierResultatUpdateComponent } from 'app/entities/fichier-resultat/fichier-resultat-update.component';
import { FichierResultatService } from 'app/entities/fichier-resultat/fichier-resultat.service';
import { FichierResultat } from 'app/shared/model/fichier-resultat.model';

describe('Component Tests', () => {
    describe('FichierResultat Management Update Component', () => {
        let comp: FichierResultatUpdateComponent;
        let fixture: ComponentFixture<FichierResultatUpdateComponent>;
        let service: FichierResultatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FichierResultatUpdateComponent]
            })
                .overrideTemplate(FichierResultatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FichierResultatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FichierResultatService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FichierResultat(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fichierResultat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FichierResultat();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.fichierResultat = entity;
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
