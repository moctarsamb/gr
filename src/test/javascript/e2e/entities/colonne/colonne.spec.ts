/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ColonneComponentsPage, ColonneDeleteDialog, ColonneUpdatePage } from './colonne.page-object';

const expect = chai.expect;

describe('Colonne e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let colonneUpdatePage: ColonneUpdatePage;
    let colonneComponentsPage: ColonneComponentsPage;
    let colonneDeleteDialog: ColonneDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Colonnes', async () => {
        await navBarPage.goToEntity('colonne');
        colonneComponentsPage = new ColonneComponentsPage();
        await browser.wait(ec.visibilityOf(colonneComponentsPage.title), 5000);
        expect(await colonneComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.colonne.home.title');
    });

    it('should load create Colonne page', async () => {
        await colonneComponentsPage.clickOnCreateButton();
        colonneUpdatePage = new ColonneUpdatePage();
        expect(await colonneUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.colonne.home.createOrEditLabel');
        await colonneUpdatePage.cancel();
    });

    it('should create and save Colonnes', async () => {
        const nbButtonsBeforeCreate = await colonneComponentsPage.countDeleteButtons();

        await colonneComponentsPage.clickOnCreateButton();
        await promise.all([
            colonneUpdatePage.setLibelleInput('libelle'),
            colonneUpdatePage.setDescriptionInput('description'),
            colonneUpdatePage.setAliasInput('alias'),
            // colonneUpdatePage.typeExtractionRequeteeSelectLastOption(),
            colonneUpdatePage.fluxSelectLastOption()
        ]);
        expect(await colonneUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await colonneUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await colonneUpdatePage.getAliasInput()).to.eq('alias');
        await colonneUpdatePage.save();
        expect(await colonneUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await colonneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Colonne', async () => {
        const nbButtonsBeforeDelete = await colonneComponentsPage.countDeleteButtons();
        await colonneComponentsPage.clickOnLastDeleteButton();

        colonneDeleteDialog = new ColonneDeleteDialog();
        expect(await colonneDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.colonne.delete.question');
        await colonneDeleteDialog.clickOnConfirmButton();

        expect(await colonneComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
