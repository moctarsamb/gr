/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { ColonneDetailComponent } from 'app/entities/colonne/colonne-detail.component';
import { Colonne } from 'app/shared/model/colonne.model';

describe('Component Tests', () => {
    describe('Colonne Management Detail Component', () => {
        let comp: ColonneDetailComponent;
        let fixture: ComponentFixture<ColonneDetailComponent>;
        const route = ({ data: of({ colonne: new Colonne(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [ColonneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ColonneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ColonneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.colonne).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
