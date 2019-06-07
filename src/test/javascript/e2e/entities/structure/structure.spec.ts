/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StructureComponentsPage, StructureDeleteDialog, StructureUpdatePage } from './structure.page-object';

const expect = chai.expect;

describe('Structure e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let structureUpdatePage: StructureUpdatePage;
    let structureComponentsPage: StructureComponentsPage;
    let structureDeleteDialog: StructureDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Structures', async () => {
        await navBarPage.goToEntity('structure');
        structureComponentsPage = new StructureComponentsPage();
        await browser.wait(ec.visibilityOf(structureComponentsPage.title), 5000);
        expect(await structureComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.structure.home.title');
    });

    it('should load create Structure page', async () => {
        await structureComponentsPage.clickOnCreateButton();
        structureUpdatePage = new StructureUpdatePage();
        expect(await structureUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.structure.home.createOrEditLabel');
        await structureUpdatePage.cancel();
    });

    it('should create and save Structures', async () => {
        const nbButtonsBeforeCreate = await structureComponentsPage.countDeleteButtons();

        await structureComponentsPage.clickOnCreateButton();
        await promise.all([
            structureUpdatePage.setCodeInput('code'),
            structureUpdatePage.setLibelleInput('libelle'),
            structureUpdatePage.filialeSelectLastOption()
        ]);
        expect(await structureUpdatePage.getCodeInput()).to.eq('code');
        expect(await structureUpdatePage.getLibelleInput()).to.eq('libelle');
        await structureUpdatePage.save();
        expect(await structureUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await structureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Structure', async () => {
        const nbButtonsBeforeDelete = await structureComponentsPage.countDeleteButtons();
        await structureComponentsPage.clickOnLastDeleteButton();

        structureDeleteDialog = new StructureDeleteDialog();
        expect(await structureDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.structure.delete.question');
        await structureDeleteDialog.clickOnConfirmButton();

        expect(await structureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
