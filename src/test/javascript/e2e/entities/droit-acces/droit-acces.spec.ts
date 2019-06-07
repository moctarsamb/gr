/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DroitAccesComponentsPage, DroitAccesDeleteDialog, DroitAccesUpdatePage } from './droit-acces.page-object';

const expect = chai.expect;

describe('DroitAcces e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let droitAccesUpdatePage: DroitAccesUpdatePage;
    let droitAccesComponentsPage: DroitAccesComponentsPage;
    let droitAccesDeleteDialog: DroitAccesDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DroitAcces', async () => {
        await navBarPage.goToEntity('droit-acces');
        droitAccesComponentsPage = new DroitAccesComponentsPage();
        await browser.wait(ec.visibilityOf(droitAccesComponentsPage.title), 5000);
        expect(await droitAccesComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.droitAcces.home.title');
    });

    it('should load create DroitAcces page', async () => {
        await droitAccesComponentsPage.clickOnCreateButton();
        droitAccesUpdatePage = new DroitAccesUpdatePage();
        expect(await droitAccesUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.droitAcces.home.createOrEditLabel');
        await droitAccesUpdatePage.cancel();
    });

    it('should create and save DroitAcces', async () => {
        const nbButtonsBeforeCreate = await droitAccesComponentsPage.countDeleteButtons();

        await droitAccesComponentsPage.clickOnCreateButton();
        await promise.all([droitAccesUpdatePage.setCodeInput('code'), droitAccesUpdatePage.setLibelleInput('libelle')]);
        expect(await droitAccesUpdatePage.getCodeInput()).to.eq('code');
        expect(await droitAccesUpdatePage.getLibelleInput()).to.eq('libelle');
        await droitAccesUpdatePage.save();
        expect(await droitAccesUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await droitAccesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DroitAcces', async () => {
        const nbButtonsBeforeDelete = await droitAccesComponentsPage.countDeleteButtons();
        await droitAccesComponentsPage.clickOnLastDeleteButton();

        droitAccesDeleteDialog = new DroitAccesDeleteDialog();
        expect(await droitAccesDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.droitAcces.delete.question');
        await droitAccesDeleteDialog.clickOnConfirmButton();

        expect(await droitAccesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
