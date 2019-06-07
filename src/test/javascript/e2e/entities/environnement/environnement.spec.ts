/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EnvironnementComponentsPage, EnvironnementDeleteDialog, EnvironnementUpdatePage } from './environnement.page-object';

const expect = chai.expect;

describe('Environnement e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let environnementUpdatePage: EnvironnementUpdatePage;
    let environnementComponentsPage: EnvironnementComponentsPage;
    let environnementDeleteDialog: EnvironnementDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Environnements', async () => {
        await navBarPage.goToEntity('environnement');
        environnementComponentsPage = new EnvironnementComponentsPage();
        await browser.wait(ec.visibilityOf(environnementComponentsPage.title), 5000);
        expect(await environnementComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.environnement.home.title');
    });

    it('should load create Environnement page', async () => {
        await environnementComponentsPage.clickOnCreateButton();
        environnementUpdatePage = new EnvironnementUpdatePage();
        expect(await environnementUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.environnement.home.createOrEditLabel'
        );
        await environnementUpdatePage.cancel();
    });

    it('should create and save Environnements', async () => {
        const nbButtonsBeforeCreate = await environnementComponentsPage.countDeleteButtons();

        await environnementComponentsPage.clickOnCreateButton();
        await promise.all([environnementUpdatePage.setCodeInput('code'), environnementUpdatePage.setLibelleInput('libelle')]);
        expect(await environnementUpdatePage.getCodeInput()).to.eq('code');
        expect(await environnementUpdatePage.getLibelleInput()).to.eq('libelle');
        await environnementUpdatePage.save();
        expect(await environnementUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await environnementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Environnement', async () => {
        const nbButtonsBeforeDelete = await environnementComponentsPage.countDeleteButtons();
        await environnementComponentsPage.clickOnLastDeleteButton();

        environnementDeleteDialog = new EnvironnementDeleteDialog();
        expect(await environnementDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.environnement.delete.question'
        );
        await environnementDeleteDialog.clickOnConfirmButton();

        expect(await environnementComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
