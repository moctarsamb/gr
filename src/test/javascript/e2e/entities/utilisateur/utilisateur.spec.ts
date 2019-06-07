/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UtilisateurComponentsPage, UtilisateurDeleteDialog, UtilisateurUpdatePage } from './utilisateur.page-object';

const expect = chai.expect;

describe('Utilisateur e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let utilisateurUpdatePage: UtilisateurUpdatePage;
    let utilisateurComponentsPage: UtilisateurComponentsPage;
    let utilisateurDeleteDialog: UtilisateurDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Utilisateurs', async () => {
        await navBarPage.goToEntity('utilisateur');
        utilisateurComponentsPage = new UtilisateurComponentsPage();
        await browser.wait(ec.visibilityOf(utilisateurComponentsPage.title), 5000);
        expect(await utilisateurComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.utilisateur.home.title');
    });

    it('should load create Utilisateur page', async () => {
        await utilisateurComponentsPage.clickOnCreateButton();
        utilisateurUpdatePage = new UtilisateurUpdatePage();
        expect(await utilisateurUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.utilisateur.home.createOrEditLabel'
        );
        await utilisateurUpdatePage.cancel();
    });

    it('should create and save Utilisateurs', async () => {
        const nbButtonsBeforeCreate = await utilisateurComponentsPage.countDeleteButtons();

        await utilisateurComponentsPage.clickOnCreateButton();
        await promise.all([
            utilisateurUpdatePage.setMatriculeInput('matricule'),
            utilisateurUpdatePage.setUsernameInput('username'),
            utilisateurUpdatePage.setFirstNameInput('firstName'),
            utilisateurUpdatePage.setLastNameInput('lastName'),
            utilisateurUpdatePage.setEmailInput('email'),
            utilisateurUpdatePage.userSelectLastOption(),
            utilisateurUpdatePage.droitAccesSelectLastOption(),
            utilisateurUpdatePage.filialeSelectLastOption(),
            utilisateurUpdatePage.profilSelectLastOption()
        ]);
        expect(await utilisateurUpdatePage.getMatriculeInput()).to.eq('matricule');
        expect(await utilisateurUpdatePage.getUsernameInput()).to.eq('username');
        expect(await utilisateurUpdatePage.getFirstNameInput()).to.eq('firstName');
        expect(await utilisateurUpdatePage.getLastNameInput()).to.eq('lastName');
        expect(await utilisateurUpdatePage.getEmailInput()).to.eq('email');
        const selectedEstActif = utilisateurUpdatePage.getEstActifInput();
        if (await selectedEstActif.isSelected()) {
            await utilisateurUpdatePage.getEstActifInput().click();
            expect(await utilisateurUpdatePage.getEstActifInput().isSelected()).to.be.false;
        } else {
            await utilisateurUpdatePage.getEstActifInput().click();
            expect(await utilisateurUpdatePage.getEstActifInput().isSelected()).to.be.true;
        }
        await utilisateurUpdatePage.save();
        expect(await utilisateurUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await utilisateurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Utilisateur', async () => {
        const nbButtonsBeforeDelete = await utilisateurComponentsPage.countDeleteButtons();
        await utilisateurComponentsPage.clickOnLastDeleteButton();

        utilisateurDeleteDialog = new UtilisateurDeleteDialog();
        expect(await utilisateurDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.utilisateur.delete.question');
        await utilisateurDeleteDialog.clickOnConfirmButton();

        expect(await utilisateurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
