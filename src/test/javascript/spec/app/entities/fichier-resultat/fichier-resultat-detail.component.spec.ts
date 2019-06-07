/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestionrequisitionsfrontendgatewayTestModule } from '../../../test.module';
import { FichierResultatDetailComponent } from 'app/entities/fichier-resultat/fichier-resultat-detail.component';
import { FichierResultat } from 'app/shared/model/fichier-resultat.model';

describe('Component Tests', () => {
    describe('FichierResultat Management Detail Component', () => {
        let comp: FichierResultatDetailComponent;
        let fixture: ComponentFixture<FichierResultatDetailComponent>;
        const route = ({ data: of({ fichierResultat: new FichierResultat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestionrequisitionsfrontendgatewayTestModule],
                declarations: [FichierResultatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FichierResultatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FichierResultatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fichierResultat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
