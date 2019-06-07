/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProvenanceComponentsPage, ProvenanceDeleteDialog, ProvenanceUpdatePage } from './provenance.page-object';

const expect = chai.expect;

describe('Provenance e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let provenanceUpdatePage: ProvenanceUpdatePage;
    let provenanceComponentsPage: ProvenanceComponentsPage;
    let provenanceDeleteDialog: ProvenanceDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Provenances', async () => {
        await navBarPage.goToEntity('provenance');
        provenanceComponentsPage = new ProvenanceComponentsPage();
        await browser.wait(ec.visibilityOf(provenanceComponentsPage.title), 5000);
        expect(await provenanceComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.provenance.home.title');
    });

    it('should load create Provenance page', async () => {
        await provenanceComponentsPage.clickOnCreateButton();
        provenanceUpdatePage = new ProvenanceUpdatePage();
        expect(await provenanceUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.provenance.home.createOrEditLabel');
        await provenanceUpdatePage.cancel();
    });

    it('should create and save Provenances', async () => {
        const nbButtonsBeforeCreate = await provenanceComponentsPage.countDeleteButtons();

        await provenanceComponentsPage.clickOnCreateButton();
        await promise.all([
            provenanceUpdatePage.setCodeInput('code'),
            provenanceUpdatePage.setLibelleInput('libelle'),
            provenanceUpdatePage.setEmailInput('email')
        ]);
        expect(await provenanceUpdatePage.getCodeInput()).to.eq('code');
        expect(await provenanceUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await provenanceUpdatePage.getEmailInput()).to.eq('email');
        await provenanceUpdatePage.save();
        expect(await provenanceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await provenanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Provenance', async () => {
        const nbButtonsBeforeDelete = await provenanceComponentsPage.countDeleteButtons();
        await provenanceComponentsPage.clickOnLastDeleteButton();

        provenanceDeleteDialog = new ProvenanceDeleteDialog();
        expect(await provenanceDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.provenance.delete.question');
        await provenanceDeleteDialog.clickOnConfirmButton();

        expect(await provenanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
