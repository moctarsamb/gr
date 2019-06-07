/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfilComponentsPage, ProfilDeleteDialog, ProfilUpdatePage } from './profil.page-object';

const expect = chai.expect;

describe('Profil e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let profilUpdatePage: ProfilUpdatePage;
    let profilComponentsPage: ProfilComponentsPage;
    let profilDeleteDialog: ProfilDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Profils', async () => {
        await navBarPage.goToEntity('profil');
        profilComponentsPage = new ProfilComponentsPage();
        await browser.wait(ec.visibilityOf(profilComponentsPage.title), 5000);
        expect(await profilComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.profil.home.title');
    });

    it('should load create Profil page', async () => {
        await profilComponentsPage.clickOnCreateButton();
        profilUpdatePage = new ProfilUpdatePage();
        expect(await profilUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.profil.home.createOrEditLabel');
        await profilUpdatePage.cancel();
    });

    it('should create and save Profils', async () => {
        const nbButtonsBeforeCreate = await profilComponentsPage.countDeleteButtons();

        await profilComponentsPage.clickOnCreateButton();
        await promise.all([
            profilUpdatePage.setLibelleInput('libelle'),
            profilUpdatePage.setDescriptionInput('description'),
            // profilUpdatePage.colonneSelectLastOption(),
            // profilUpdatePage.typeExtractionSelectLastOption(),
            profilUpdatePage.administrateurProfilSelectLastOption()
        ]);
        expect(await profilUpdatePage.getLibelleInput()).to.eq('libelle');
        expect(await profilUpdatePage.getDescriptionInput()).to.eq('description');
        await profilUpdatePage.save();
        expect(await profilUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await profilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Profil', async () => {
        const nbButtonsBeforeDelete = await profilComponentsPage.countDeleteButtons();
        await profilComponentsPage.clickOnLastDeleteButton();

        profilDeleteDialog = new ProfilDeleteDialog();
        expect(await profilDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.profil.delete.question');
        await profilDeleteDialog.clickOnConfirmButton();

        expect(await profilComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
