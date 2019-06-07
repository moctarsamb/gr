/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DimensionComponentsPage, DimensionDeleteDialog, DimensionUpdatePage } from './dimension.page-object';

const expect = chai.expect;

describe('Dimension e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let dimensionUpdatePage: DimensionUpdatePage;
    let dimensionComponentsPage: DimensionComponentsPage;
    let dimensionDeleteDialog: DimensionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Dimensions', async () => {
        await navBarPage.goToEntity('dimension');
        dimensionComponentsPage = new DimensionComponentsPage();
        await browser.wait(ec.visibilityOf(dimensionComponentsPage.title), 5000);
        expect(await dimensionComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.dimension.home.title');
    });

    it('should load create Dimension page', async () => {
        await dimensionComponentsPage.clickOnCreateButton();
        dimensionUpdatePage = new DimensionUpdatePage();
        expect(await dimensionUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.dimension.home.createOrEditLabel');
        await dimensionUpdatePage.cancel();
    });

    it('should create and save Dimensions', async () => {
        const nbButtonsBeforeCreate = await dimensionComponentsPage.countDeleteButtons();

        await dimensionComponentsPage.clickOnCreateButton();
        await promise.all([
            // dimensionUpdatePage.monitorSelectLastOption(),
            dimensionUpdatePage.baseSelectLastOption(),
            dimensionUpdatePage.fluxSelectLastOption(),
            dimensionUpdatePage.typeExtractionSelectLastOption()
        ]);
        await dimensionUpdatePage.save();
        expect(await dimensionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await dimensionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Dimension', async () => {
        const nbButtonsBeforeDelete = await dimensionComponentsPage.countDeleteButtons();
        await dimensionComponentsPage.clickOnLastDeleteButton();

        dimensionDeleteDialog = new DimensionDeleteDialog();
        expect(await dimensionDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.dimension.delete.question');
        await dimensionDeleteDialog.clickOnConfirmButton();

        expect(await dimensionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
