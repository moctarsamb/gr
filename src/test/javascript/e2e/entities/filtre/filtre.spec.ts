/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FiltreComponentsPage, FiltreDeleteDialog, FiltreUpdatePage } from './filtre.page-object';

const expect = chai.expect;

describe('Filtre e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let filtreUpdatePage: FiltreUpdatePage;
    let filtreComponentsPage: FiltreComponentsPage;
    let filtreDeleteDialog: FiltreDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Filtres', async () => {
        await navBarPage.goToEntity('filtre');
        filtreComponentsPage = new FiltreComponentsPage();
        await browser.wait(ec.visibilityOf(filtreComponentsPage.title), 5000);
        expect(await filtreComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.filtre.home.title');
    });

    it('should load create Filtre page', async () => {
        await filtreComponentsPage.clickOnCreateButton();
        filtreUpdatePage = new FiltreUpdatePage();
        expect(await filtreUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.filtre.home.createOrEditLabel');
        await filtreUpdatePage.cancel();
    });

    it('should create and save Filtres', async () => {
        const nbButtonsBeforeCreate = await filtreComponentsPage.countDeleteButtons();

        await filtreComponentsPage.clickOnCreateButton();
        await promise.all([
            // filtreUpdatePage.clauseSelectLastOption(),
            filtreUpdatePage.operateurLogiqueSelectLastOption()
        ]);
        await filtreUpdatePage.save();
        expect(await filtreUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await filtreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Filtre', async () => {
        const nbButtonsBeforeDelete = await filtreComponentsPage.countDeleteButtons();
        await filtreComponentsPage.clickOnLastDeleteButton();

        filtreDeleteDialog = new FiltreDeleteDialog();
        expect(await filtreDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.filtre.delete.question');
        await filtreDeleteDialog.clickOnConfirmButton();

        expect(await filtreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
