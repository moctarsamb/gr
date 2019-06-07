/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ColonneUpdateComponent } from 'app/entities/colonne/colonne-update.component';
import { ColonneService } from 'app/entities/colonne/colonne.service';
import { Colonne } from 'app/shared/model/colonne.model';

describe('Component Tests', () => {
    describe('Colonne Management Update Component', () => {
        let comp: ColonneUpdateComponent;
        let fixture: ComponentFixture<ColonneUpdateComponent>;
        let service: ColonneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ColonneUpdateComponent]
            })
                .overrideTemplate(ColonneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ColonneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ColonneService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Colonne(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.colonne = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Colonne();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.colonne = entity;
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
