/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypologieComponentsPage, TypologieDeleteDialog, TypologieUpdatePage } from './typologie.page-object';

const expect = chai.expect;

describe('Typologie e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let typologieUpdatePage: TypologieUpdatePage;
    let typologieComponentsPage: TypologieComponentsPage;
    let typologieDeleteDialog: TypologieDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Typologies', async () => {
        await navBarPage.goToEntity('typologie');
        typologieComponentsPage = new TypologieComponentsPage();
        await browser.wait(ec.visibilityOf(typologieComponentsPage.title), 5000);
        expect(await typologieComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.typologie.home.title');
    });

    it('should load create Typologie page', async () => {
        await typologieComponentsPage.clickOnCreateButton();
        typologieUpdatePage = new TypologieUpdatePage();
        expect(await typologieUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.typologie.home.createOrEditLabel');
        await typologieUpdatePage.cancel();
    });

    it('should create and save Typologies', async () => {
        const nbButtonsBeforeCreate = await typologieComponentsPage.countDeleteButtons();

        await typologieComponentsPage.clickOnCreateButton();
        await promise.all([
            typologieUpdatePage.setCodeInput('code'),
            typologieUpdatePage.setLibelleInput('libelle'),
            typologieUpdatePage.setConditionInput('condition'),
            typologieUpdatePage.setTraitementInput('traitement')
        ]);
        expect(await typologieUpdatePage.getCodeInput()).to.eq('code');
        expect(await typologieUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await typologieUpdatePage.getConditionInput()).to.eq('condition');
        expect(await typologieUpdatePage.getTraitementInput()).to.eq('traitement');
        await typologieUpdatePage.save();
        expect(await typologieUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await typologieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Typologie', async () => {
        const nbButtonsBeforeDelete = await typologieComponentsPage.countDeleteButtons();
        await typologieComponentsPage.clickOnLastDeleteButton();

        typologieDeleteDialog = new TypologieDeleteDialog();
        expect(await typologieDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.typologie.delete.question');
        await typologieDeleteDialog.clickOnConfirmButton();

        expect(await typologieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
