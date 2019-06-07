/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EnvoiResultatComponentsPage, EnvoiResultatDeleteDialog, EnvoiResultatUpdatePage } from './envoi-resultat.page-object';

const expect = chai.expect;

describe('EnvoiResultat e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let envoiResultatUpdatePage: EnvoiResultatUpdatePage;
    let envoiResultatComponentsPage: EnvoiResultatComponentsPage;
    let envoiResultatDeleteDialog: EnvoiResultatDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load EnvoiResultats', async () => {
        await navBarPage.goToEntity('envoi-resultat');
        envoiResultatComponentsPage = new EnvoiResultatComponentsPage();
        await browser.wait(ec.visibilityOf(envoiResultatComponentsPage.title), 5000);
        expect(await envoiResultatComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.envoiResultat.home.title');
    });

    it('should load create EnvoiResultat page', async () => {
        await envoiResultatComponentsPage.clickOnCreateButton();
        envoiResultatUpdatePage = new EnvoiResultatUpdatePage();
        expect(await envoiResultatUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.envoiResultat.home.createOrEditLabel'
        );
        await envoiResultatUpdatePage.cancel();
    });

    it('should create and save EnvoiResultats', async () => {
        const nbButtonsBeforeCreate = await envoiResultatComponentsPage.countDeleteButtons();

        await envoiResultatComponentsPage.clickOnCreateButton();
        await promise.all([
            envoiResultatUpdatePage.setEmailInput('email'),
            envoiResultatUpdatePage.setDateEnvoiInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            envoiResultatUpdatePage.fichierResultatSelectLastOption(),
            envoiResultatUpdatePage.utilisateurSelectLastOption()
        ]);
        expect(await envoiResultatUpdatePage.getEmailInput()).to.eq('email');
        expect(await envoiResultatUpdatePage.getDateEnvoiInput()).to.contain('2001-01-01T02:30');
        await envoiResultatUpdatePage.save();
        expect(await envoiResultatUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await envoiResultatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last EnvoiResultat', async () => {
        const nbButtonsBeforeDelete = await envoiResultatComponentsPage.countDeleteButtons();
        await envoiResultatComponentsPage.clickOnLastDeleteButton();

        envoiResultatDeleteDialog = new EnvoiResultatDeleteDialog();
        expect(await envoiResultatDeleteDialog.getDialogTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.envoiResultat.delete.question'
        );
        await envoiResultatDeleteDialog.clickOnConfirmButton();

        expect(await envoiResultatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
