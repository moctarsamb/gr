/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FilialeComponentsPage, FilialeDeleteDialog, FilialeUpdatePage } from './filiale.page-object';

const expect = chai.expect;

describe('Filiale e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let filialeUpdatePage: FilialeUpdatePage;
    let filialeComponentsPage: FilialeComponentsPage;
    let filialeDeleteDialog: FilialeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Filiales', async () => {
        await navBarPage.goToEntity('filiale');
        filialeComponentsPage = new FilialeComponentsPage();
        await browser.wait(ec.visibilityOf(filialeComponentsPage.title), 5000);
        expect(await filialeComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.filiale.home.title');
    });

    it('should load create Filiale page', async () => {
        await filialeComponentsPage.clickOnCreateButton();
        filialeUpdatePage = new FilialeUpdatePage();
        expect(await filialeUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.filiale.home.createOrEditLabel');
        await filialeUpdatePage.cancel();
    });

    it('should create and save Filiales', async () => {
        const nbButtonsBeforeCreate = await filialeComponentsPage.countDeleteButtons();

        await filialeComponentsPage.clickOnCreateButton();
        await promise.all([
            filialeUpdatePage.setCodeInput('code'),
            filialeUpdatePage.setLibelleInput('libelle')
            // filialeUpdatePage.environnementSelectLastOption(),
            // filialeUpdatePage.profilSelectLastOption(),
        ]);
        expect(await filialeUpdatePage.getCodeInput()).to.eq('code');
        expect(await filialeUpdatePage.getLibelleInput()).to.eq('libelle');
        await filialeUpdatePage.save();
        expect(await filialeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await filialeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Filiale', async () => {
        const nbButtonsBeforeDelete = await filialeComponentsPage.countDeleteButtons();
        await filialeComponentsPage.clickOnLastDeleteButton();

        filialeDeleteDialog = new FilialeDeleteDialog();
        expect(await filialeDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.filiale.delete.question');
        await filialeDeleteDialog.clickOnConfirmButton();

        expect(await filialeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
