/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BaseComponentsPage, BaseDeleteDialog, BaseUpdatePage } from './base.page-object';

const expect = chai.expect;

describe('Base e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let baseUpdatePage: BaseUpdatePage;
    let baseComponentsPage: BaseComponentsPage;
    let baseDeleteDialog: BaseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Bases', async () => {
        await navBarPage.goToEntity('base');
        baseComponentsPage = new BaseComponentsPage();
        await browser.wait(ec.visibilityOf(baseComponentsPage.title), 5000);
        expect(await baseComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.base.home.title');
    });

    it('should load create Base page', async () => {
        await baseComponentsPage.clickOnCreateButton();
        baseUpdatePage = new BaseUpdatePage();
        expect(await baseUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.base.home.createOrEditLabel');
        await baseUpdatePage.cancel();
    });

    it('should create and save Bases', async () => {
        const nbButtonsBeforeCreate = await baseComponentsPage.countDeleteButtons();

        await baseComponentsPage.clickOnCreateButton();
        await promise.all([baseUpdatePage.setLibelleInput('libelle'), baseUpdatePage.setDescriptionInput('description')]);
        expect(await baseUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await baseUpdatePage.getDescriptionInput()).to.eq('description');
        await baseUpdatePage.save();
        expect(await baseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await baseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Base', async () => {
        const nbButtonsBeforeDelete = await baseComponentsPage.countDeleteButtons();
        await baseComponentsPage.clickOnLastDeleteButton();

        baseDeleteDialog = new BaseDeleteDialog();
        expect(await baseDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.base.delete.question');
        await baseDeleteDialog.clickOnConfirmButton();

        expect(await baseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
