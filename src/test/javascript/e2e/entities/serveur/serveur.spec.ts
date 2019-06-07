/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ServeurComponentsPage, ServeurDeleteDialog, ServeurUpdatePage } from './serveur.page-object';

const expect = chai.expect;

describe('Serveur e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let serveurUpdatePage: ServeurUpdatePage;
    let serveurComponentsPage: ServeurComponentsPage;
    let serveurDeleteDialog: ServeurDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Serveurs', async () => {
        await navBarPage.goToEntity('serveur');
        serveurComponentsPage = new ServeurComponentsPage();
        await browser.wait(ec.visibilityOf(serveurComponentsPage.title), 5000);
        expect(await serveurComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.serveur.home.title');
    });

    it('should load create Serveur page', async () => {
        await serveurComponentsPage.clickOnCreateButton();
        serveurUpdatePage = new ServeurUpdatePage();
        expect(await serveurUpdatePage.getPageTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.serveur.home.createOrEditLabel');
        await serveurUpdatePage.cancel();
    });

    it('should create and save Serveurs', async () => {
        const nbButtonsBeforeCreate = await serveurComponentsPage.countDeleteButtons();

        await serveurComponentsPage.clickOnCreateButton();
        await promise.all([
            serveurUpdatePage.setNomInput('nom'),
            serveurUpdatePage.setAdresseIpInput('adresseIp'),
            serveurUpdatePage.setLoginInput('login'),
            serveurUpdatePage.setMotDePasseInput('motDePasse'),
            serveurUpdatePage.setRepertoireDepotsInput('repertoireDepots'),
            serveurUpdatePage.setRepertoireResultatsInput('repertoireResultats'),
            serveurUpdatePage.setRepertoireParametresInput('repertoireParametres')
        ]);
        expect(await serveurUpdatePage.getNomInput()).to.eq('nom');
        expect(await serveurUpdatePage.getAdresseIpInput()).to.eq('adresseIp');
        expect(await serveurUpdatePage.getLoginInput()).to.eq('login');
        expect(await serveurUpdatePage.getMotDePasseInput()).to.eq('motDePasse');
        expect(await serveurUpdatePage.getRepertoireDepotsInput()).to.eq('repertoireDepots');
        expect(await serveurUpdatePage.getRepertoireResultatsInput()).to.eq('repertoireResultats');
        expect(await serveurUpdatePage.getRepertoireParametresInput()).to.eq('repertoireParametres');
        const selectedEstActif = serveurUpdatePage.getEstActifInput();
        if (await selectedEstActif.isSelected()) {
            await serveurUpdatePage.getEstActifInput().click();
            expect(await serveurUpdatePage.getEstActifInput().isSelected()).to.be.false;
        } else {
            await serveurUpdatePage.getEstActifInput().click();
            expect(await serveurUpdatePage.getEstActifInput().isSelected()).to.be.true;
        }
        await serveurUpdatePage.save();
        expect(await serveurUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await serveurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Serveur', async () => {
        const nbButtonsBeforeDelete = await serveurComponentsPage.countDeleteButtons();
        await serveurComponentsPage.clickOnLastDeleteButton();

        serveurDeleteDialog = new ServeurDeleteDialog();
        expect(await serveurDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.serveur.delete.question');
        await serveurDeleteDialog.clickOnConfirmButton();

        expect(await serveurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
