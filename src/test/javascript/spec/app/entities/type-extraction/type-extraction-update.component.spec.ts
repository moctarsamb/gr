/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { TypeExtractionUpdateComponent } from 'app/entities/type-extraction/type-extraction-update.component';
import { TypeExtractionService } from 'app/entities/type-extraction/type-extraction.service';
import { TypeExtraction } from 'app/shared/model/type-extraction.model';

describe('Component Tests', () => {
    describe('TypeExtraction Management Update Component', () => {
        let comp: TypeExtractionUpdateComponent;
        let fixture: ComponentFixture<TypeExtractionUpdateComponent>;
        let service: TypeExtractionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [TypeExtractionUpdateComponent]
            })
                .overrideTemplate(TypeExtractionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeExtractionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeExtractionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeExtraction(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeExtraction = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TypeExtraction();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.typeExtraction = entity;
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
