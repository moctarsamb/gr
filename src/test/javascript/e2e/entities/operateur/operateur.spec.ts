/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OperateurComponentsPage, OperateurDeleteDialog, OperateurUpdatePage } from './operateur.page-object';

const expect = chai.expect;

describe('Operateur e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let operateurUpdatePage: OperateurUpdatePage;
    let operateurComponentsPage: OperateurComponentsPage;
    let operateurDeleteDialog: OperateurDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Operateurs', async () => {
        await navBarPage.goToEntity('operateur');
        operateurComponentsPage = new OperateurComponentsPage();
        await browser.wait(ec.visibilityOf(operateurComponentsPage.title), 5000);
        expect(await operateurComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operateur.home.title');
    });

    it('should load create Operateur page', async () => {
        await operateurComponentsPage.clickOnCreateButton();
        operateurUpdatePage = new OperateurUpdatePage();
        expect(await operateurUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operateur.home.createOrEditLabel');
        await operateurUpdatePage.cancel();
    });

    it('should create and save Operateurs', async () => {
        const nbButtonsBeforeCreate = await operateurComponentsPage.countDeleteButtons();

        await operateurComponentsPage.clickOnCreateButton();
        await promise.all([operateurUpdatePage.setOperateurInput('operateur')]);
        expect(await operateurUpdatePage.getOperateurInput()).to.eq('operateur');
        await operateurUpdatePage.save();
        expect(await operateurUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await operateurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Operateur', async () => {
        const nbButtonsBeforeDelete = await operateurComponentsPage.countDeleteButtons();
        await operateurComponentsPage.clickOnLastDeleteButton();

        operateurDeleteDialog = new OperateurDeleteDialog();
        expect(await operateurDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operateur.delete.question');
        await operateurDeleteDialog.clickOnConfirmButton();

        expect(await operateurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
