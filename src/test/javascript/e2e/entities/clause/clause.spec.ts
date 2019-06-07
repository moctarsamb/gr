/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClauseComponentsPage, ClauseDeleteDialog, ClauseUpdatePage } from './clause.page-object';

const expect = chai.expect;

describe('Clause e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let clauseUpdatePage: ClauseUpdatePage;
    let clauseComponentsPage: ClauseComponentsPage;
    let clauseDeleteDialog: ClauseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Clauses', async () => {
        await navBarPage.goToEntity('clause');
        clauseComponentsPage = new ClauseComponentsPage();
        await browser.wait(ec.visibilityOf(clauseComponentsPage.title), 5000);
        expect(await clauseComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.clause.home.title');
    });

    it('should load create Clause page', async () => {
        await clauseComponentsPage.clickOnCreateButton();
        clauseUpdatePage = new ClauseUpdatePage();
        expect(await clauseUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.clause.home.createOrEditLabel');
        await clauseUpdatePage.cancel();
    });

    it('should create and save Clauses', async () => {
        const nbButtonsBeforeCreate = await clauseComponentsPage.countDeleteButtons();

        await clauseComponentsPage.clickOnCreateButton();
        await promise.all([
            clauseUpdatePage.setPrefixeInput('prefixe'),
            clauseUpdatePage.setSuffixeInput('suffixe'),
            // clauseUpdatePage.operandeSelectLastOption(),
            clauseUpdatePage.operateurSelectLastOption()
        ]);
        expect(await clauseUpdatePage.getPrefixeInput()).to.eq('prefixe');
        expect(await clauseUpdatePage.getSuffixeInput()).to.eq('suffixe');
        await clauseUpdatePage.save();
        expect(await clauseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await clauseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Clause', async () => {
        const nbButtonsBeforeDelete = await clauseComponentsPage.countDeleteButtons();
        await clauseComponentsPage.clickOnLastDeleteButton();

        clauseDeleteDialog = new ClauseDeleteDialog();
        expect(await clauseDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.clause.delete.question');
        await clauseDeleteDialog.clickOnConfirmButton();

        expect(await clauseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
