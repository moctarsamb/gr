/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FilialeUpdateComponent } from 'app/entities/filiale/filiale-update.component';
import { FilialeService } from 'app/entities/filiale/filiale.service';
import { Filiale } from 'app/shared/model/filiale.model';

describe('Component Tests', () => {
    describe('Filiale Management Update Component', () => {
        let comp: FilialeUpdateComponent;
        let fixture: ComponentFixture<FilialeUpdateComponent>;
        let service: FilialeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FilialeUpdateComponent]
            })
                .overrideTemplate(FilialeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FilialeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FilialeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Filiale(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.filiale = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Filiale();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.filiale = entity;
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
