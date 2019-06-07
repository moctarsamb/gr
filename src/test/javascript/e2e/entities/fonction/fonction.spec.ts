/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FonctionComponentsPage, FonctionDeleteDialog, FonctionUpdatePage } from './fonction.page-object';

const expect = chai.expect;

describe('Fonction e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let fonctionUpdatePage: FonctionUpdatePage;
    let fonctionComponentsPage: FonctionComponentsPage;
    let fonctionDeleteDialog: FonctionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Fonctions', async () => {
        await navBarPage.goToEntity('fonction');
        fonctionComponentsPage = new FonctionComponentsPage();
        await browser.wait(ec.visibilityOf(fonctionComponentsPage.title), 5000);
        expect(await fonctionComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.fonction.home.title');
    });

    it('should load create Fonction page', async () => {
        await fonctionComponentsPage.clickOnCreateButton();
        fonctionUpdatePage = new FonctionUpdatePage();
        expect(await fonctionUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.fonction.home.createOrEditLabel');
        await fonctionUpdatePage.cancel();
    });

    it('should create and save Fonctions', async () => {
        const nbButtonsBeforeCreate = await fonctionComponentsPage.countDeleteButtons();

        await fonctionComponentsPage.clickOnCreateButton();
        await promise.all([fonctionUpdatePage.setNomInput('nom')]);
        expect(await fonctionUpdatePage.getNomInput()).to.eq('nom');
        await fonctionUpdatePage.save();
        expect(await fonctionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await fonctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Fonction', async () => {
        const nbButtonsBeforeDelete = await fonctionComponentsPage.countDeleteButtons();
        await fonctionComponentsPage.clickOnLastDeleteButton();

        fonctionDeleteDialog = new FonctionDeleteDialog();
        expect(await fonctionDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.fonction.delete.question');
        await fonctionDeleteDialog.clickOnConfirmButton();

        expect(await fonctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
