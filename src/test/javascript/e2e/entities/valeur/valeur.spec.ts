/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ValeurComponentsPage, ValeurDeleteDialog, ValeurUpdatePage } from './valeur.page-object';

const expect = chai.expect;

describe('Valeur e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let valeurUpdatePage: ValeurUpdatePage;
    let valeurComponentsPage: ValeurComponentsPage;
    let valeurDeleteDialog: ValeurDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Valeurs', async () => {
        await navBarPage.goToEntity('valeur');
        valeurComponentsPage = new ValeurComponentsPage();
        await browser.wait(ec.visibilityOf(valeurComponentsPage.title), 5000);
        expect(await valeurComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.valeur.home.title');
    });

    it('should load create Valeur page', async () => {
        await valeurComponentsPage.clickOnCreateButton();
        valeurUpdatePage = new ValeurUpdatePage();
        expect(await valeurUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.valeur.home.createOrEditLabel');
        await valeurUpdatePage.cancel();
    });

    it('should create and save Valeurs', async () => {
        const nbButtonsBeforeCreate = await valeurComponentsPage.countDeleteButtons();

        await valeurComponentsPage.clickOnCreateButton();
        await promise.all([valeurUpdatePage.setValeurInput('valeur')]);
        expect(await valeurUpdatePage.getValeurInput()).to.eq('valeur');
        await valeurUpdatePage.save();
        expect(await valeurUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await valeurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Valeur', async () => {
        const nbButtonsBeforeDelete = await valeurComponentsPage.countDeleteButtons();
        await valeurComponentsPage.clickOnLastDeleteButton();

        valeurDeleteDialog = new ValeurDeleteDialog();
        expect(await valeurDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.valeur.delete.question');
        await valeurDeleteDialog.clickOnConfirmButton();

        expect(await valeurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
