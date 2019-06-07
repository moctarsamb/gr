import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'jointure',
                loadChildren: './jointure/jointure.module#GestionrequisitionsfrontendgatewayJointureModule'
            },
            {
                path: 'dimension',
                loadChildren: './dimension/dimension.module#GestionrequisitionsfrontendgatewayDimensionModule'
            },
            {
                path: 'monitor',
                loadChildren: './monitor/monitor.module#GestionrequisitionsfrontendgatewayMonitorModule'
            },
            {
                path: 'filtre',
                loadChildren: './filtre/filtre.module#GestionrequisitionsfrontendgatewayFiltreModule'
            },
            {
                path: 'critere',
                loadChildren: './critere/critere.module#GestionrequisitionsfrontendgatewayCritereModule'
            },
            {
                path: 'fonction',
                loadChildren: './fonction/fonction.module#GestionrequisitionsfrontendgatewayFonctionModule'
            },
            {
                path: 'base',
                loadChildren: './base/base.module#GestionrequisitionsfrontendgatewayBaseModule'
            },
            {
                path: 'operande',
                loadChildren: './operande/operande.module#GestionrequisitionsfrontendgatewayOperandeModule'
            },
            {
                path: 'valeur',
                loadChildren: './valeur/valeur.module#GestionrequisitionsfrontendgatewayValeurModule'
            },
            {
                path: 'flux',
                loadChildren: './flux/flux.module#GestionrequisitionsfrontendgatewayFluxModule'
            },
            {
                path: 'operateur-logique',
                loadChildren: './operateur-logique/operateur-logique.module#GestionrequisitionsfrontendgatewayOperateurLogiqueModule'
            },
            {
                path: 'operateur',
                loadChildren: './operateur/operateur.module#GestionrequisitionsfrontendgatewayOperateurModule'
            },
            {
                path: 'clause',
                loadChildren: './clause/clause.module#GestionrequisitionsfrontendgatewayClauseModule'
            },
            {
                path: 'utilisateur',
                loadChildren: './utilisateur/utilisateur.module#GestionrequisitionsfrontendgatewayUtilisateurModule'
            },
            {
                path: 'profil',
                loadChildren: './profil/profil.module#GestionrequisitionsfrontendgatewayProfilModule'
            },
            {
                path: 'type-extraction',
                loadChildren: './type-extraction/type-extraction.module#GestionrequisitionsfrontendgatewayTypeExtractionModule'
            },
            {
                path: 'type-jointure',
                loadChildren: './type-jointure/type-jointure.module#GestionrequisitionsfrontendgatewayTypeJointureModule'
            },
            {
                path: 'colonne',
                loadChildren: './colonne/colonne.module#GestionrequisitionsfrontendgatewayColonneModule'
            },
            {
                path: 'filiale',
                loadChildren: './filiale/filiale.module#GestionrequisitionsfrontendgatewayFilialeModule'
            },
            {
                path: 'structure',
                loadChildren: './structure/structure.module#GestionrequisitionsfrontendgatewayStructureModule'
            },
            {
                path: 'resultat',
                loadChildren: './resultat/resultat.module#GestionrequisitionsfrontendgatewayResultatModule'
            },
            {
                path: 'fichier-resultat',
                loadChildren: './fichier-resultat/fichier-resultat.module#GestionrequisitionsfrontendgatewayFichierResultatModule'
            },
            {
                path: 'champs-recherche',
                loadChildren: './champs-recherche/champs-recherche.module#GestionrequisitionsfrontendgatewayChampsRechercheModule'
            },
            {
                path: 'environnement',
                loadChildren: './environnement/environnement.module#GestionrequisitionsfrontendgatewayEnvironnementModule'
            },
            {
                path: 'requisition',
                loadChildren: './requisition/requisition.module#GestionrequisitionsfrontendgatewayRequisitionModule'
            },
            {
                path: 'envoi-resultat',
                loadChildren: './envoi-resultat/envoi-resultat.module#GestionrequisitionsfrontendgatewayEnvoiResultatModule'
            },
            {
                path: 'notification',
                loadChildren: './notification/notification.module#GestionrequisitionsfrontendgatewayNotificationModule'
            },
            {
                path: 'droit-acces',
                loadChildren: './droit-acces/droit-acces.module#GestionrequisitionsfrontendgatewayDroitAccesModule'
            },
            {
                path: 'provenance',
                loadChildren: './provenance/provenance.module#GestionrequisitionsfrontendgatewayProvenanceModule'
            },
            {
                path: 'typologie',
                loadChildren: './typologie/typologie.module#GestionrequisitionsfrontendgatewayTypologieModule'
            },
            {
                path: 'serveur',
                loadChildren: './serveur/serveur.module#GestionrequisitionsfrontendgatewayServeurModule'
            },
            {
                path: 'parametrage-application',
                loadChildren:
                    './parametrage-application/parametrage-application.module#GestionrequisitionsfrontendgatewayParametrageApplicationModule'
            },
            {
                path: 'jointure',
                loadChildren: './jointure/jointure.module#GestionrequisitionsfrontendgatewayJointureModule'
            },
            {
                path: 'dimension',
                loadChildren: './dimension/dimension.module#GestionrequisitionsfrontendgatewayDimensionModule'
            },
            {
                path: 'monitor',
                loadChildren: './monitor/monitor.module#GestionrequisitionsfrontendgatewayMonitorModule'
            },
            {
                path: 'filtre',
                loadChildren: './filtre/filtre.module#GestionrequisitionsfrontendgatewayFiltreModule'
            },
            {
                path: 'critere',
                loadChildren: './critere/critere.module#GestionrequisitionsfrontendgatewayCritereModule'
            },
            {
                path: 'fonction',
                loadChildren: './fonction/fonction.module#GestionrequisitionsfrontendgatewayFonctionModule'
            },
            {
                path: 'base',
                loadChildren: './base/base.module#GestionrequisitionsfrontendgatewayBaseModule'
            },
            {
                path: 'operande',
                loadChildren: './operande/operande.module#GestionrequisitionsfrontendgatewayOperandeModule'
            },
            {
                path: 'valeur',
                loadChildren: './valeur/valeur.module#GestionrequisitionsfrontendgatewayValeurModule'
            },
            {
                path: 'flux',
                loadChildren: './flux/flux.module#GestionrequisitionsfrontendgatewayFluxModule'
            },
            {
                path: 'operateur-logique',
                loadChildren: './operateur-logique/operateur-logique.module#GestionrequisitionsfrontendgatewayOperateurLogiqueModule'
            },
            {
                path: 'operateur',
                loadChildren: './operateur/operateur.module#GestionrequisitionsfrontendgatewayOperateurModule'
            },
            {
                path: 'clause',
                loadChildren: './clause/clause.module#GestionrequisitionsfrontendgatewayClauseModule'
            },
            {
                path: 'utilisateur',
                loadChildren: './utilisateur/utilisateur.module#GestionrequisitionsfrontendgatewayUtilisateurModule'
            },
            {
                path: 'profil',
                loadChildren: './profil/profil.module#GestionrequisitionsfrontendgatewayProfilModule'
            },
            {
                path: 'type-extraction',
                loadChildren: './type-extraction/type-extraction.module#GestionrequisitionsfrontendgatewayTypeExtractionModule'
            },
            {
                path: 'type-jointure',
                loadChildren: './type-jointure/type-jointure.module#GestionrequisitionsfrontendgatewayTypeJointureModule'
            },
            {
                path: 'colonne',
                loadChildren: './colonne/colonne.module#GestionrequisitionsfrontendgatewayColonneModule'
            },
            {
                path: 'filiale',
                loadChildren: './filiale/filiale.module#GestionrequisitionsfrontendgatewayFilialeModule'
            },
            {
                path: 'structure',
                loadChildren: './structure/structure.module#GestionrequisitionsfrontendgatewayStructureModule'
            },
            {
                path: 'resultat',
                loadChildren: './resultat/resultat.module#GestionrequisitionsfrontendgatewayResultatModule'
            },
            {
                path: 'fichier-resultat',
                loadChildren: './fichier-resultat/fichier-resultat.module#GestionrequisitionsfrontendgatewayFichierResultatModule'
            },
            {
                path: 'champs-recherche',
                loadChildren: './champs-recherche/champs-recherche.module#GestionrequisitionsfrontendgatewayChampsRechercheModule'
            },
            {
                path: 'environnement',
                loadChildren: './environnement/environnement.module#GestionrequisitionsfrontendgatewayEnvironnementModule'
            },
            {
                path: 'requisition',
                loadChildren: './requisition/requisition.module#GestionrequisitionsfrontendgatewayRequisitionModule'
            },
            {
                path: 'envoi-resultat',
                loadChildren: './envoi-resultat/envoi-resultat.module#GestionrequisitionsfrontendgatewayEnvoiResultatModule'
            },
            {
                path: 'notification',
                loadChildren: './notification/notification.module#GestionrequisitionsfrontendgatewayNotificationModule'
            },
            {
                path: 'droit-acces',
                loadChildren: './droit-acces/droit-acces.module#GestionrequisitionsfrontendgatewayDroitAccesModule'
            },
            {
                path: 'provenance',
                loadChildren: './provenance/provenance.module#GestionrequisitionsfrontendgatewayProvenanceModule'
            },
            {
                path: 'typologie',
                loadChildren: './typologie/typologie.module#GestionrequisitionsfrontendgatewayTypologieModule'
            },
            {
                path: 'serveur',
                loadChildren: './serveur/serveur.module#GestionrequisitionsfrontendgatewayServeurModule'
            },
            {
                path: 'parametrage-application',
                loadChildren:
                    './parametrage-application/parametrage-application.module#GestionrequisitionsfrontendgatewayParametrageApplicationModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestionrequisitionsfrontendgatewayEntityModule {}
