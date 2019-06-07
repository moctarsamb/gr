/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OperandeComponentsPage, OperandeDeleteDialog, OperandeUpdatePage } from './operande.page-object';

const expect = chai.expect;

describe('Operande e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let operandeUpdatePage: OperandeUpdatePage;
    let operandeComponentsPage: OperandeComponentsPage;
    let operandeDeleteDialog: OperandeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Operandes', async () => {
        await navBarPage.goToEntity('operande');
        operandeComponentsPage = new OperandeComponentsPage();
        await browser.wait(ec.visibilityOf(operandeComponentsPage.title), 5000);
        expect(await operandeComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operande.home.title');
    });

    it('should load create Operande page', async () => {
        await operandeComponentsPage.clickOnCreateButton();
        operandeUpdatePage = new OperandeUpdatePage();
        expect(await operandeUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operande.home.createOrEditLabel');
        await operandeUpdatePage.cancel();
    });

    it('should create and save Operandes', async () => {
        const nbButtonsBeforeCreate = await operandeComponentsPage.countDeleteButtons();

        await operandeComponentsPage.clickOnCreateButton();
        await promise.all([
            // operandeUpdatePage.colonneSelectLastOption(),
            // operandeUpdatePage.valeurSelectLastOption(),
            operandeUpdatePage.fonctionSelectLastOption()
        ]);
        await operandeUpdatePage.save();
        expect(await operandeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await operandeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Operande', async () => {
        const nbButtonsBeforeDelete = await operandeComponentsPage.countDeleteButtons();
        await operandeComponentsPage.clickOnLastDeleteButton();

        operandeDeleteDialog = new OperandeDeleteDialog();
        expect(await operandeDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operande.delete.question');
        await operandeDeleteDialog.clickOnConfirmButton();

        expect(await operandeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
