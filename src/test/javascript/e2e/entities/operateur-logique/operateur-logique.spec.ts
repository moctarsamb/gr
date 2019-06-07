/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OperateurLogiqueComponentsPage, OperateurLogiqueDeleteDialog, OperateurLogiqueUpdatePage } from './operateur-logique.page-object';

const expect = chai.expect;

describe('OperateurLogique e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let operateurLogiqueUpdatePage: OperateurLogiqueUpdatePage;
    let operateurLogiqueComponentsPage: OperateurLogiqueComponentsPage;
    let operateurLogiqueDeleteDialog: OperateurLogiqueDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load OperateurLogiques', async () => {
        await navBarPage.goToEntity('operateur-logique');
        operateurLogiqueComponentsPage = new OperateurLogiqueComponentsPage();
        await browser.wait(ec.visibilityOf(operateurLogiqueComponentsPage.title), 5000);
        expect(await operateurLogiqueComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.operateurLogique.home.title');
    });

    it('should load create OperateurLogique page', async () => {
        await operateurLogiqueComponentsPage.clickOnCreateButton();
        operateurLogiqueUpdatePage = new OperateurLogiqueUpdatePage();
        expect(await operateurLogiqueUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.operateurLogique.home.createOrEditLabel'
        );
        await operateurLogiqueUpdatePage.cancel();
    });

    it('should create and save OperateurLogiques', async () => {
        const nbButtonsBeforeCreate = await operateurLogiqueComponentsPage.countDeleteButtons();

        await operateurLogiqueComponentsPage.clickOnCreateButton();
        await promise.all([operateurLogiqueUpdatePage.setOperateurLogiqueInput('operateurLogique')]);
        expect(await operateurLogiqueUpdatePage.getOperateurLogiqueInput()).to.eq('operateurLogique');
        await operateurLogiqueUpdatePage.save();
        expect(await operateurLogiqueUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await operateurLogiqueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last OperateurLogique', async () => {
        const nbButtonsBeforeDelete = await operateurLogiqueComponentsPage.countDeleteButtons();
        await operateurLogiqueComponentsPage.clickOnLastDeleteButton();

        operateurLogiqueDeleteDialog = new OperateurLogiqueDeleteDialog();
        expect(await operateurLogiqueDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.operateurLogique.delete.question'
        );
        await operateurLogiqueDeleteDialog.clickOnConfirmButton();

        expect(await operateurLogiqueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
