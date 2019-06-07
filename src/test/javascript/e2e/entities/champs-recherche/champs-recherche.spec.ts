/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ChampsRechercheComponentsPage, ChampsRechercheDeleteDialog, ChampsRechercheUpdatePage } from './champs-recherche.page-object';

const expect = chai.expect;

describe('ChampsRecherche e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let champsRechercheUpdatePage: ChampsRechercheUpdatePage;
    let champsRechercheComponentsPage: ChampsRechercheComponentsPage;
    let champsRechercheDeleteDialog: ChampsRechercheDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ChampsRecherches', async () => {
        await navBarPage.goToEntity('champs-recherche');
        champsRechercheComponentsPage = new ChampsRechercheComponentsPage();
        await browser.wait(ec.visibilityOf(champsRechercheComponentsPage.title), 5000);
        expect(await champsRechercheComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.champsRecherche.home.title');
    });

    it('should load create ChampsRecherche page', async () => {
        await champsRechercheComponentsPage.clickOnCreateButton();
        champsRechercheUpdatePage = new ChampsRechercheUpdatePage();
        expect(await champsRechercheUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.champsRecherche.home.createOrEditLabel'
        );
        await champsRechercheUpdatePage.cancel();
    });

    it('should create and save ChampsRecherches', async () => {
        const nbButtonsBeforeCreate = await champsRechercheComponentsPage.countDeleteButtons();

        await champsRechercheComponentsPage.clickOnCreateButton();
        await promise.all([
            champsRechercheUpdatePage.setChampsInput('champs'),
            champsRechercheUpdatePage.setDateDebutExtractionInput('2000-12-31'),
            champsRechercheUpdatePage.setDateFinExtractionInput('2000-12-31'),
            champsRechercheUpdatePage.colonneSelectLastOption(),
            champsRechercheUpdatePage.environnementSelectLastOption(),
            champsRechercheUpdatePage.filialeSelectLastOption(),
            champsRechercheUpdatePage.requisitionSelectLastOption(),
            champsRechercheUpdatePage.typeExtractionSelectLastOption()
        ]);
        expect(await champsRechercheUpdatePage.getChampsInput()).to.eq('champs');
        expect(await champsRechercheUpdatePage.getDateDebutExtractionInput()).to.eq('2000-12-31');
        expect(await champsRechercheUpdatePage.getDateFinExtractionInput()).to.eq('2000-12-31');
        await champsRechercheUpdatePage.save();
        expect(await champsRechercheUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await champsRechercheComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ChampsRecherche', async () => {
        const nbButtonsBeforeDelete = await champsRechercheComponentsPage.countDeleteButtons();
        await champsRechercheComponentsPage.clickOnLastDeleteButton();

        champsRechercheDeleteDialog = new ChampsRechercheDeleteDialog();
        expect(await champsRechercheDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.champsRecherche.delete.question'
        );
        await champsRechercheDeleteDialog.clickOnConfirmButton();

        expect(await champsRechercheComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
