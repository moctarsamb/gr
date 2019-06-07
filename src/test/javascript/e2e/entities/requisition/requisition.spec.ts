/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RequisitionComponentsPage, RequisitionDeleteDialog, RequisitionUpdatePage } from './requisition.page-object';

const expect = chai.expect;

describe('Requisition e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let requisitionUpdatePage: RequisitionUpdatePage;
    let requisitionComponentsPage: RequisitionComponentsPage;
    let requisitionDeleteDialog: RequisitionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Requisitions', async () => {
        await navBarPage.goToEntity('requisition');
        requisitionComponentsPage = new RequisitionComponentsPage();
        await browser.wait(ec.visibilityOf(requisitionComponentsPage.title), 5000);
        expect(await requisitionComponentsPage.getTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.requisition.home.title');
    });

    it('should load create Requisition page', async () => {
        await requisitionComponentsPage.clickOnCreateButton();
        requisitionUpdatePage = new RequisitionUpdatePage();
        expect(await requisitionUpdatePage.getPageTitle()).to.eq(
            'gestionrequisitionsfrontendgatewayApp.requisition.home.createOrEditLabel'
        );
        await requisitionUpdatePage.cancel();
    });

    it('should create and save Requisitions', async () => {
        const nbButtonsBeforeCreate = await requisitionComponentsPage.countDeleteButtons();

        await requisitionComponentsPage.clickOnCreateButton();
        await promise.all([
            requisitionUpdatePage.setNumeroArriveeDemandeInput('5'),
            requisitionUpdatePage.setNumeroPvInput('5'),
            requisitionUpdatePage.setDateSaisiePvInput('2000-12-31'),
            requisitionUpdatePage.setDateArriveeDemandeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            requisitionUpdatePage.setDateSaisieDemandeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            requisitionUpdatePage.setDateReponseInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            requisitionUpdatePage.setDateRenvoieResultatInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            requisitionUpdatePage.statusSelectLastOption(),
            requisitionUpdatePage.etatSelectLastOption(),
            requisitionUpdatePage.provenanceSelectLastOption(),
            requisitionUpdatePage.structureSelectLastOption(),
            requisitionUpdatePage.utilisateurSelectLastOption()
        ]);
        expect(await requisitionUpdatePage.getNumeroArriveeDemandeInput()).to.eq('5');
        expect(await requisitionUpdatePage.getNumeroPvInput()).to.eq('5');
        expect(await requisitionUpdatePage.getDateSaisiePvInput()).to.eq('2000-12-31');
        expect(await requisitionUpdatePage.getDateArriveeDemandeInput()).to.contain('2001-01-01T02:30');
        expect(await requisitionUpdatePage.getDateSaisieDemandeInput()).to.contain('2001-01-01T02:30');
        const selectedEnvoiResultatAutomatique = requisitionUpdatePage.getEnvoiResultatAutomatiqueInput();
        if (await selectedEnvoiResultatAutomatique.isSelected()) {
            await requisitionUpdatePage.getEnvoiResultatAutomatiqueInput().click();
            expect(await requisitionUpdatePage.getEnvoiResultatAutomatiqueInput().isSelected()).to.be.false;
        } else {
            await requisitionUpdatePage.getEnvoiResultatAutomatiqueInput().click();
            expect(await requisitionUpdatePage.getEnvoiResultatAutomatiqueInput().isSelected()).to.be.true;
        }
        expect(await requisitionUpdatePage.getDateReponseInput()).to.contain('2001-01-01T02:30');
        expect(await requisitionUpdatePage.getDateRenvoieResultatInput()).to.contain('2001-01-01T02:30');
        await requisitionUpdatePage.save();
        expect(await requisitionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await requisitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Requisition', async () => {
        const nbButtonsBeforeDelete = await requisitionComponentsPage.countDeleteButtons();
        await requisitionComponentsPage.clickOnLastDeleteButton();

        requisitionDeleteDialog = new RequisitionDeleteDialog();
        expect(await requisitionDeleteDialog.getDialogTitle()).to.eq('gestionrequisitionsfrontendgatewayApp.requisition.delete.question');
        await requisitionDeleteDialog.clickOnConfirmButton();

        expect(await requisitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
