/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CritereComponentsPage, CritereDeleteDialog, CritereUpdatePage } from './critere.page-object';

const expect = chai.expect;

describe('Critere e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let critereUpdatePage: CritereUpdatePage;
    let critereComponentsPage: CritereComponentsPage;
    let critereDeleteDialog: CritereDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Criteres', async () => {
        await navBarPage.goToEntity('critere');
        critereComponentsPage = new CritereComponentsPage();
        await browser.wait(ec.visibilityOf(critereComponentsPage.title), 5000);
        expect(await critereComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.critere.home.title');
    });

    it('should load create Critere page', async () => {
        await critereComponentsPage.clickOnCreateButton();
        critereUpdatePage = new CritereUpdatePage();
        expect(await critereUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.critere.home.createOrEditLabel');
        await critereUpdatePage.cancel();
    });

    it('should create and save Criteres', async () => {
        const nbButtonsBeforeCreate = await critereComponentsPage.countDeleteButtons();

        await critereComponentsPage.clickOnCreateButton();
        await promise.all([critereUpdatePage.clauseSelectLastOption(), critereUpdatePage.operateurLogiqueSelectLastOption()]);
        await critereUpdatePage.save();
        expect(await critereUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await critereComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Critere', async () => {
        const nbButtonsBeforeDelete = await critereComponentsPage.countDeleteButtons();
        await critereComponentsPage.clickOnLastDeleteButton();

        critereDeleteDialog = new CritereDeleteDialog();
        expect(await critereDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.critere.delete.question');
        await critereDeleteDialog.clickOnConfirmButton();

        expect(await critereComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
