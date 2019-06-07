/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FichierResultatComponentsPage, FichierResultatDeleteDialog, FichierResultatUpdatePage } from './fichier-resultat.page-object';

const expect = chai.expect;

describe('FichierResultat e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let fichierResultatUpdatePage: FichierResultatUpdatePage;
    let fichierResultatComponentsPage: FichierResultatComponentsPage;
    let fichierResultatDeleteDialog: FichierResultatDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load FichierResultats', async () => {
        await navBarPage.goToEntity('fichier-resultat');
        fichierResultatComponentsPage = new FichierResultatComponentsPage();
        await browser.wait(ec.visibilityOf(fichierResultatComponentsPage.title), 5000);
        expect(await fichierResultatComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.fichierResultat.home.title');
    });

    it('should load create FichierResultat page', async () => {
        await fichierResultatComponentsPage.clickOnCreateButton();
        fichierResultatUpdatePage = new FichierResultatUpdatePage();
        expect(await fichierResultatUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.fichierResultat.home.createOrEditLabel'
        );
        await fichierResultatUpdatePage.cancel();
    });

    it('should create and save FichierResultats', async () => {
        const nbButtonsBeforeCreate = await fichierResultatComponentsPage.countDeleteButtons();

        await fichierResultatComponentsPage.clickOnCreateButton();
        await promise.all([
            fichierResultatUpdatePage.setCheminFichierInput('cheminFichier'),
            fichierResultatUpdatePage.formatSelectLastOption(),
            fichierResultatUpdatePage.resultatSelectLastOption()
        ]);
        expect(await fichierResultatUpdatePage.getCheminFichierInput()).to.eq('cheminFichier');
        const selectedAvecStatistiques = fichierResultatUpdatePage.getAvecStatistiquesInput();
        if (await selectedAvecStatistiques.isSelected()) {
            await fichierResultatUpdatePage.getAvecStatistiquesInput().click();
            expect(await fichierResultatUpdatePage.getAvecStatistiquesInput().isSelected()).to.be.false;
        } else {
            await fichierResultatUpdatePage.getAvecStatistiquesInput().click();
            expect(await fichierResultatUpdatePage.getAvecStatistiquesInput().isSelected()).to.be.true;
        }
        await fichierResultatUpdatePage.save();
        expect(await fichierResultatUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await fichierResultatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last FichierResultat', async () => {
        const nbButtonsBeforeDelete = await fichierResultatComponentsPage.countDeleteButtons();
        await fichierResultatComponentsPage.clickOnLastDeleteButton();

        fichierResultatDeleteDialog = new FichierResultatDeleteDialog();
        expect(await fichierResultatDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.fichierResultat.delete.question'
        );
        await fichierResultatDeleteDialog.clickOnConfirmButton();

        expect(await fichierResultatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
