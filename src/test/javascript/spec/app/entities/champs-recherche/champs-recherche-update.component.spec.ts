/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ChampsRechercheUpdateComponent } from 'app/entities/champs-recherche/champs-recherche-update.component';
import { ChampsRechercheService } from 'app/entities/champs-recherche/champs-recherche.service';
import { ChampsRecherche } from 'app/shared/model/champs-recherche.model';

describe('Component Tests', () => {
    describe('ChampsRecherche Management Update Component', () => {
        let comp: ChampsRechercheUpdateComponent;
        let fixture: ComponentFixture<ChampsRechercheUpdateComponent>;
        let service: ChampsRechercheService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ChampsRechercheUpdateComponent]
            })
                .overrideTemplate(ChampsRechercheUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChampsRechercheUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChampsRechercheService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ChampsRecherche(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.champsRecherche = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ChampsRecherche();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.champsRecherche = entity;
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
