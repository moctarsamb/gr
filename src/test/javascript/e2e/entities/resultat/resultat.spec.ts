/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ResultatComponentsPage, ResultatDeleteDialog, ResultatUpdatePage } from './resultat.page-object';

const expect = chai.expect;

describe('Resultat e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let resultatUpdatePage: ResultatUpdatePage;
    let resultatComponentsPage: ResultatComponentsPage;
    let resultatDeleteDialog: ResultatDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Resultats', async () => {
        await navBarPage.goToEntity('resultat');
        resultatComponentsPage = new ResultatComponentsPage();
        await browser.wait(ec.visibilityOf(resultatComponentsPage.title), 5000);
        expect(await resultatComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.resultat.home.title');
    });

    it('should load create Resultat page', async () => {
        await resultatComponentsPage.clickOnCreateButton();
        resultatUpdatePage = new ResultatUpdatePage();
        expect(await resultatUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.resultat.home.createOrEditLabel');
        await resultatUpdatePage.cancel();
    });

    it('should create and save Resultats', async () => {
        const nbButtonsBeforeCreate = await resultatComponentsPage.countDeleteButtons();

        await resultatComponentsPage.clickOnCreateButton();
        await promise.all([
            resultatUpdatePage.statusSelectLastOption(),
            resultatUpdatePage.etatSelectLastOption(),
            resultatUpdatePage.champsRechercheSelectLastOption()
        ]);
        await resultatUpdatePage.save();
        expect(await resultatUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await resultatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Resultat', async () => {
        const nbButtonsBeforeDelete = await resultatComponentsPage.countDeleteButtons();
        await resultatComponentsPage.clickOnLastDeleteButton();

        resultatDeleteDialog = new ResultatDeleteDialog();
        expect(await resultatDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.resultat.delete.question');
        await resultatDeleteDialog.clickOnConfirmButton();

        expect(await resultatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
