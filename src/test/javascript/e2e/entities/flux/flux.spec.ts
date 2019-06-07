/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FluxComponentsPage, FluxDeleteDialog, FluxUpdatePage } from './flux.page-object';

const expect = chai.expect;

describe('Flux e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let fluxUpdatePage: FluxUpdatePage;
    let fluxComponentsPage: FluxComponentsPage;
    let fluxDeleteDialog: FluxDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Fluxes', async () => {
        await navBarPage.goToEntity('flux');
        fluxComponentsPage = new FluxComponentsPage();
        await browser.wait(ec.visibilityOf(fluxComponentsPage.title), 5000);
        expect(await fluxComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.flux.home.title');
    });

    it('should load create Flux page', async () => {
        await fluxComponentsPage.clickOnCreateButton();
        fluxUpdatePage = new FluxUpdatePage();
        expect(await fluxUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.flux.home.createOrEditLabel');
        await fluxUpdatePage.cancel();
    });

    it('should create and save Fluxes', async () => {
        const nbButtonsBeforeCreate = await fluxComponentsPage.countDeleteButtons();

        await fluxComponentsPage.clickOnCreateButton();
        await promise.all([
            fluxUpdatePage.setLibelleInput('libelle'),
            fluxUpdatePage.setDescriptionInput('description'),
            fluxUpdatePage.baseSelectLastOption()
        ]);
        expect(await fluxUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await fluxUpdatePage.getDescriptionInput()).to.eq('description');
        await fluxUpdatePage.save();
        expect(await fluxUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await fluxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Flux', async () => {
        const nbButtonsBeforeDelete = await fluxComponentsPage.countDeleteButtons();
        await fluxComponentsPage.clickOnLastDeleteButton();

        fluxDeleteDialog = new FluxDeleteDialog();
        expect(await fluxDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.flux.delete.question');
        await fluxDeleteDialog.clickOnConfirmButton();

        expect(await fluxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
