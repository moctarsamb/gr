/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ParametrageApplicationComponentsPage,
    ParametrageApplicationDeleteDialog,
    ParametrageApplicationUpdatePage
} from './parametrage-application.page-object';

const expect = chai.expect;

describe('ParametrageApplication e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let parametrageApplicationUpdatePage: ParametrageApplicationUpdatePage;
    let parametrageApplicationComponentsPage: ParametrageApplicationComponentsPage;
    let parametrageApplicationDeleteDialog: ParametrageApplicationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ParametrageApplications', async () => {
        await navBarPage.goToEntity('parametrage-application');
        parametrageApplicationComponentsPage = new ParametrageApplicationComponentsPage();
        await browser.wait(ec.visibilityOf(parametrageApplicationComponentsPage.title), 5000);
        expect(await parametrageApplicationComponentsPage.getTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.title'
        );
    });

    it('should load create ParametrageApplication page', async () => {
        await parametrageApplicationComponentsPage.clickOnCreateButton();
        parametrageApplicationUpdatePage = new ParametrageApplicationUpdatePage();
        expect(await parametrageApplicationUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.parametrageApplication.home.createOrEditLabel'
        );
        await parametrageApplicationUpdatePage.cancel();
    });

    it('should create and save ParametrageApplications', async () => {
        const nbButtonsBeforeCreate = await parametrageApplicationComponentsPage.countDeleteButtons();

        await parametrageApplicationComponentsPage.clickOnCreateButton();
        await promise.all([
            parametrageApplicationUpdatePage.setNomFichierInput('nomFichier'),
            parametrageApplicationUpdatePage.setCheminFichierInput('cheminFichier')
        ]);
        expect(await parametrageApplicationUpdatePage.getNomFichierInput()).to.eq('nomFichier');
        expect(await parametrageApplicationUpdatePage.getCheminFichierInput()).to.eq('cheminFichier');
        await parametrageApplicationUpdatePage.save();
        expect(await parametrageApplicationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await parametrageApplicationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ParametrageApplication', async () => {
        const nbButtonsBeforeDelete = await parametrageApplicationComponentsPage.countDeleteButtons();
        await parametrageApplicationComponentsPage.clickOnLastDeleteButton();

        parametrageApplicationDeleteDialog = new ParametrageApplicationDeleteDialog();
        expect(await parametrageApplicationDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.parametrageApplication.delete.question'
        );
        await parametrageApplicationDeleteDialog.clickOnConfirmButton();

        expect(await parametrageApplicationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
