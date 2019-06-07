/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypeExtractionComponentsPage, TypeExtractionDeleteDialog, TypeExtractionUpdatePage } from './type-extraction.page-object';

const expect = chai.expect;

describe('TypeExtraction e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let typeExtractionUpdatePage: TypeExtractionUpdatePage;
    let typeExtractionComponentsPage: TypeExtractionComponentsPage;
    let typeExtractionDeleteDialog: TypeExtractionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TypeExtractions', async () => {
        await navBarPage.goToEntity('type-extraction');
        typeExtractionComponentsPage = new TypeExtractionComponentsPage();
        await browser.wait(ec.visibilityOf(typeExtractionComponentsPage.title), 5000);
        expect(await typeExtractionComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.typeExtraction.home.title');
    });

    it('should load create TypeExtraction page', async () => {
        await typeExtractionComponentsPage.clickOnCreateButton();
        typeExtractionUpdatePage = new TypeExtractionUpdatePage();
        expect(await typeExtractionUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.typeExtraction.home.createOrEditLabel'
        );
        await typeExtractionUpdatePage.cancel();
    });

    it('should create and save TypeExtractions', async () => {
        const nbButtonsBeforeCreate = await typeExtractionComponentsPage.countDeleteButtons();

        await typeExtractionComponentsPage.clickOnCreateButton();
        await promise.all([
            typeExtractionUpdatePage.setCodeInput('code'),
            typeExtractionUpdatePage.setLibelleInput('libelle'),
            // typeExtractionUpdatePage.monitorSelectLastOption(),
            typeExtractionUpdatePage.baseSelectLastOption(),
            typeExtractionUpdatePage.filtreSelectLastOption(),
            typeExtractionUpdatePage.fluxSelectLastOption()
        ]);
        expect(await typeExtractionUpdatePage.getCodeInput()).to.eq('code');
        expect(await typeExtractionUpdatePage.getLibelleInput()).to.eq('libelle');
        await typeExtractionUpdatePage.save();
        expect(await typeExtractionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await typeExtractionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TypeExtraction', async () => {
        const nbButtonsBeforeDelete = await typeExtractionComponentsPage.countDeleteButtons();
        await typeExtractionComponentsPage.clickOnLastDeleteButton();

        typeExtractionDeleteDialog = new TypeExtractionDeleteDialog();
        expect(await typeExtractionDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.typeExtraction.delete.question'
        );
        await typeExtractionDeleteDialog.clickOnConfirmButton();

        expect(await typeExtractionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
